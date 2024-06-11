package org.example.jct.report;

import lombok.extern.slf4j.Slf4j;
import org.example.jct.data.Query;

import java.util.List;

@Slf4j
public class ReportGenerator {

    public void generateReport(List<Query> queryList) {
        queryList.forEach(query -> {
            log.info("Query ID: {}", query.getId());
            log.info("SQL: {}", query.getSql());
            log.info("Oracle Keywords: {}", query.getKeywords());
        });
    }

}
