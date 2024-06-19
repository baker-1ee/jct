package org.example.jct.report;

import lombok.extern.slf4j.Slf4j;
import org.example.jct.data.OracleQuery;

import java.util.List;

@Slf4j
public class ConsoleReportGenerator implements ReportGenerator {

    @Override
    public void generateReport(List<OracleQuery> queries) {
        queries.forEach(query -> {
            log.info("File Path: {}", query.getFilePath());
            log.info("Query ID: {}", query.getQueryId());
            log.info("SQL: {}", query.getSql());
            log.info("Oracle Keywords: {}", query.getKeywords());
            log.info("자동 변환 가능 여부: {}", query.isAbleAutoConversion());
            log.info("안내사항: {}", query.getGuidelines());
        });
    }

}
