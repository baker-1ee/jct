package org.example.jct.converter;

import lombok.extern.slf4j.Slf4j;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QueryConverter {

    public void convert(List<File> xmlFiles, List<OracleQuery> oracleQueryList) {
        Map<ParsedQuery, OracleQuery> queryMap = oracleQueryList.stream()
                .collect(Collectors.toMap(OracleQuery::getKey, e -> e));

        MyBatisXmlParser xmlParser = new MyBatisXmlParser();

        for (File file : xmlFiles) {
            try {
                Path filePath = file.toPath();
                String content = new String(Files.readAllBytes(filePath));

                List<Element> elements = xmlParser.getElements(file);
                for (Element element : elements) {
                    ParsedQuery parsedQuery = ParsedQuery.of(file, element);

                    OracleQuery oracleQuery = queryMap.get(parsedQuery);
                    if (oracleQuery != null && oracleQuery.isAbleAutoConversion()) {
                        // 원본 쿼리
                        String sourceSql = oracleQuery.getSql();
                        // 대체할 쿼리
                        String targetSql = oracleQuery.convert();

                        // 파일 내용 중 sourceSql을 targetSql로 대체
                        if (content.contains(sourceSql)) {
                            content = content.replace(sourceSql, targetSql);
                        }
                    }
                }

                Files.write(filePath, content.getBytes());

            } catch (IOException e) {
                log.error("QueryConverter error {}", file.getPath(), e);
            }

        }
    }


}