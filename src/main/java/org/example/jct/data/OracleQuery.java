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
}
