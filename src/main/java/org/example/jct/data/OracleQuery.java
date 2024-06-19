package org.example.jct.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@EqualsAndHashCode
@ToString
@Builder
public class OracleQuery {
    private ParsedQuery query;
    @Getter
    private Set<String> keywords;

    public static OracleQuery of(ParsedQuery query, Set<String> oracleKeywords) {
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
}
