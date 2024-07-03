package org.example.jct.analyzer;

import lombok.RequiredArgsConstructor;
import org.example.jct.RuleService;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.rule.Rule;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// 키워드 분석은 대소문자 구분하지 않고, 대문자로 변환 후 비교
@Service
@RequiredArgsConstructor
public class SqlAnalyzer {

    private final RuleService ruleService;

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\b[A-Z_][A-Z0-9_]*\\b(?=\\()", Pattern.CASE_INSENSITIVE);

    public List<OracleQuery> analyze(List<ParsedQuery> queries) {
        return queries.stream()
                .map(this::analyze)
                .collect(Collectors.toList());
    }

    private OracleQuery analyze(ParsedQuery query) {
        Set<Rule> oracleKeywords = new LinkedHashSet<>();

        // Match defined rules
        Matcher matcher = ruleService.getOraclePattern().matcher(query.getSql());
        while (matcher.find()) {
            oracleKeywords.add(ruleService.findByOracleKeyword(matcher.group()));
        }

        // Match functions not defined in rules
        Matcher functionMatcher = FUNCTION_PATTERN.matcher(query.getSql());
        while (functionMatcher.find()) {
            oracleKeywords.add(ruleService.findByOracleKeyword(functionMatcher.group()));
        }

        return OracleQuery.of(query, oracleKeywords);
    }

}
