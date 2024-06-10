package org.example.jct.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class KeywordLoader {

    public static List<String> loadKeywords(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

}
