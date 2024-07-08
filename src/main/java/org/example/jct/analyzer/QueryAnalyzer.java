package org.example.jct.analyzer;

import lombok.RequiredArgsConstructor;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.rule.Rule;
import org.example.jct.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryAnalyzer {

    private final RuleService ruleService;

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\b[A-Z_][A-Z0-9_]*\\b(?=\\()", Pattern.CASE_INSENSITIVE);

    public List<AnalyzedQuery> analyze(List<ParsedQuery> queries) {
        return queries.stream()
                .map(this::analyze)
                .collect(Collectors.toList());
    }

    private AnalyzedQuery analyze(ParsedQuery query) {
        Set<Rule> rules = new LinkedHashSet<>();

        Matcher matcher = ruleService.getPattern().matcher(query.getSqlErasedComment());
        while (matcher.find()) {
            rules.add(ruleService.findKeyword(matcher.group()));
        }
        
        Matcher functionMatcher = FUNCTION_PATTERN.matcher(query.getSqlErasedComment());
        while (functionMatcher.find()) {
            ruleService.findFunctionOpt(functionMatcher.group()).ifPresent(rules::add);
        }

        return AnalyzedQuery.of(query, rules);
    }

}
