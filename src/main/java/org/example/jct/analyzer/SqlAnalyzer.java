package org.example.jct.analyzer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlAnalyzer {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\b[A-Z_][A-Z0-9_]*\\b(?=\\()", Pattern.CASE_INSENSITIVE);

    public Set<String> analyze(String sql) {
        Set<String> result = new LinkedHashSet<>();

        // Match defined keywords
        Matcher matcher = KeywordEnum.ORACLE_PATTERN.matcher(sql);
        while (matcher.find()) {
            result.add(matcher.group().toUpperCase());
        }

        // Match functions not defined in KeywordEnum
        Matcher functionMatcher = FUNCTION_PATTERN.matcher(sql);
        while (functionMatcher.find()) {
            result.add(functionMatcher.group().toUpperCase());
        }
        return result;
    }
}
