package org.example.jct.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeywordLoader {

    public static List<String> loadKeywords(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        }
    }

}
