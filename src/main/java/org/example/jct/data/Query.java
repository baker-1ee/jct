package org.example.jct.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor(staticName = "of")
public class Query {
    private String fileName;
    private String id;
    private String sql;
    private Set<String> keywords;

}
