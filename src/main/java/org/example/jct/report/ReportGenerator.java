package org.example.jct.report;

import org.example.jct.data.Query;

import java.util.List;

public interface ReportGenerator {
    void generateReport(List<Query> queryList);
}
