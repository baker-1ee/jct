package org.example.jct.report;

import java.util.Map;
import java.util.Set;

public class ReportGenerator {

    public void generateReport(Map<String, Set<String>> analysisResults) {
        analysisResults.forEach((queryId, keywords) -> {
            System.out.println("Query ID: " + queryId);
            System.out.println("Keywords: " + keywords);
            System.out.println();
        });
    }

}
