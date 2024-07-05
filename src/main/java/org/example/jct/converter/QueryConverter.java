package org.example.jct.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jct.analyzer.AnalyzedQuery;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.parser.QueryParser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryConverter {

    private final QueryParser queryParser;

    public void convert(List<File> files, List<AnalyzedQuery> analyzedQueryList) {
        Map<ParsedQuery, AnalyzedQuery> queryMap = analyzedQueryList.stream()
                .collect(Collectors.toMap(AnalyzedQuery::getKey, e -> e));

        for (File file : files) {
            try {
                Path filePath = file.toPath();
                String content = Files.readString(filePath, StandardCharsets.UTF_8);

                List<Element> elements = queryParser.getElements(file);
                for (Element element : elements) {
                    ParsedQuery parsedQuery = ParsedQuery.of(file, element);

                    AnalyzedQuery analyzedQuery = queryMap.get(parsedQuery);
                    if (analyzedQuery != null && analyzedQuery.isAbleAutoConversion()) {
                        // 원본 쿼리
                        String sourceSql = analyzedQuery.getSql();
                        // 대체할 쿼리
                        String targetSql = analyzedQuery.convert();

                        // 파일 내용 중 sourceSql을 targetSql로 대체
                        if (content.contains(sourceSql)) {
                            content = content.replace(sourceSql, targetSql);
                        }
                    }
                }

                Files.writeString(filePath, content, StandardCharsets.UTF_8);

            } catch (IOException e) {
                log.error("QueryConverter error {}", file.getPath(), e);
            }

        }
    }


}