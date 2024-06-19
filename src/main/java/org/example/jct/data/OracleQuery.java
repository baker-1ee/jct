package org.example.jct.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.example.jct.analyzer.KeywordEnum;

import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
@Builder
public class OracleQuery {
    private ParsedQuery query;
    private Set<KeywordEnum> keywords;

    public static OracleQuery of(ParsedQuery query, Set<KeywordEnum> oracleKeywords) {
        return OracleQuery.builder()
                .query(query)
                .keywords(oracleKeywords)
                .build();
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
                .map(KeywordEnum::getOracle)
                .collect(Collectors.joining(", "));
    }

    public Boolean isAbleAutoConversion() {
        return keywords.stream()
                .allMatch(KeywordEnum::isAvailAutoConversion);
    }

    public String getGuidelines() {
        return keywords.stream()
                .map(KeywordEnum::getGuideline)
                .filter(e -> !e.isEmpty())
                .collect(Collectors.joining("\n"));
    }
}
