package org.example.jct.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.jct.analyzer.Keyword;

import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
@Builder
public class OracleQuery {
    private ParsedQuery query;
    private Set<Keyword> keywords;

    public static OracleQuery of(ParsedQuery query, Set<Keyword> oracleKeywords) {
        return OracleQuery.builder()
                .query(query)
                .keywords(oracleKeywords)
                .build();
    }

    public ParsedQuery getKey() {
        return query;
    }

    public String getFilePath() {
        return query.getFilePath();
    }

    public String getQueryId() {
        return query.getQueryId();
    }

    public String getSql() {
        return query.getSql();
    }

    public String getKeywords() {
        return keywords.stream()
                .map(Keyword::getOracle)
                .collect(Collectors.joining(", "));
    }

    public Boolean isAbleAutoConversion() {
        return keywords.stream()
                .allMatch(Keyword::isAvailAutoConversion);
    }

    public String getGuidelines() {
        return keywords.stream()
                .filter(Keyword::needToNotify)
                .map(Keyword::getGuideline)
                .collect(Collectors.joining("\n"));
    }

    public String convert() {
        String sql = query.getSql();
        for (Keyword keyword : keywords) {
            // 대소문자 구분 없이 전체 단어 일치
            sql = sql.replaceAll("(?i)\\b" + keyword.getOracle() + "\\b", keyword.getMysql());
        }
        return sql;
    }
}
