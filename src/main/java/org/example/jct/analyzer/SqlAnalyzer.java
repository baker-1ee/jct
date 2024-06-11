package org.example.jct.analyzer;

import org.example.jct.util.KeywordLoader;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SqlAnalyzer {

    private static Pattern SQL_KEYWORDS_PATTERN;

    static {
        try {
            List<String> keywords = KeywordLoader.loadKeywords("src/main/resources/oracleKeywords.txt");
            String patternString = "\\b(" + String.join("|", keywords) + ")\\b";
            SQL_KEYWORDS_PATTERN = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public Set<String> analyze(String sql) {
        return SQL_KEYWORDS_PATTERN.matcher(sql).results()  // SQL을 대문자로 변환
                .map(matchResult -> matchResult.group().toUpperCase())
                .collect(Collectors.toSet());
    }
}
