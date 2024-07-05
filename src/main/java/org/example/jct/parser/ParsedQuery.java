package org.example.jct.parser;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.w3c.dom.Element;

import java.io.File;

@EqualsAndHashCode
@ToString
@Builder
public class ParsedQuery {

    private Key key;
    @Getter
    private String sql;

    public static ParsedQuery of(File file, Element element) {
        return ParsedQuery.builder()
                .key(Key.of(file, element.getAttribute("id")))
                .sql(element.getTextContent().trim())
                .build();
    }

    public String getFilePath() {
        return key.getFilePath();
    }

    public String getQueryId() {
        return key.getId();
    }

    @EqualsAndHashCode
    @ToString
    @Builder
    @Getter
    public static class Key {

        private String filePath;
        private String id;

        private static Key of(File file, String queryId) {
            return Key.builder()
                    .filePath(file.getPath())
                    .id(queryId)
                    .build();
        }
    }

}
