package org.example.jct.report;

import org.example.jct.analyzer.AnalyzedQuery;

import java.util.List;

public interface ReportGenerator {

    void generate(List<AnalyzedQuery> queries);

}
