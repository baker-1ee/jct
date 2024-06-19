package org.example.jct.analyzer;

import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlAnalyzer {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\b[A-Z_][A-Z0-9_]*\\b(?=\\()", Pattern.CASE_INSENSITIVE);

    public OracleQuery analyze(ParsedQuery query) {
        Set<String> oracleKeywords = new LinkedHashSet<>();

        // Match defined keywords
        Matcher matcher = KeywordEnum.ORACLE_PATTERN.matcher(query.getSql());
        while (matcher.find()) {
            oracleKeywords.add(matcher.group().toUpperCase());
        }

        // Match functions not defined in KeywordEnum
        Matcher functionMatcher = FUNCTION_PATTERN.matcher(query.getSql());
        while (functionMatcher.find()) {
            oracleKeywords.add(functionMatcher.group().toUpperCase());
        }

        return OracleQuery.of(query, oracleKeywords);
    }
}
