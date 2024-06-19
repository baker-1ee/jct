package org.example.jct.report;

import org.example.jct.data.OracleQuery;

import java.util.List;

public interface ReportGenerator {
    void generateReport(List<OracleQuery> queries);
}
