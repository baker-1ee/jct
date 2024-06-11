package org.example.jct.data;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Query {
    private String id;
    private String sql;
    private Set<String> keywords;

    public static Query of(String id, String sql, Set<String> keywords) {
        return Query.builder()
                .id(id)
                .sql(sql)
                .keywords(keywords)
                .build();
    }
}
