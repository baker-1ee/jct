package org.example.jct.analyzer;

import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 키워드 분석은 대소문자 구분하지 않고, 대문자로 변환 후 비교
public class SqlAnalyzer {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\b[A-Z_][A-Z0-9_]*\\b(?=\\()", Pattern.CASE_INSENSITIVE);

    public OracleQuery analyze(ParsedQuery query) {
        Set<Keyword> oracleKeywords = new LinkedHashSet<>();

        // Match defined keywords
        Matcher matcher = KeywordEnum.ORACLE_PATTERN.matcher(query.getSql());
        while (matcher.find()) {
            Keyword knownKeyword = KeywordEnum.findByOracleKeyword(matcher.group());
            oracleKeywords.add(knownKeyword);
        }

        // Match functions not defined in KeywordEnum
        Matcher functionMatcher = FUNCTION_PATTERN.matcher(query.getSql());
        while (functionMatcher.find()) {
            Keyword knownKeyword = KeywordEnum.findByOracleKeyword(functionMatcher.group());
            oracleKeywords.add(knownKeyword);
        }

        return OracleQuery.of(query, oracleKeywords);
    }
}
