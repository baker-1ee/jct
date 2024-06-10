package org.example;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String directory = "/Users/a452609/IdeaProjects/jct/src/main/resources/mock/mybatis";

        XmlFileExplorer fileExplorer = new XmlFileExplorer();
        MyBatisXmlParser xmlParser = new MyBatisXmlParser();
        SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
        ReportGenerator reportGenerator = new ReportGenerator();

        List<File> xmlFiles = fileExplorer.findXmlFiles(directory);
        Map<String, Set<String>> analysisResults = xmlFiles.stream()
                .flatMap(file -> {
                    try {
                        Map<String, String> queries = xmlParser.parse(file);
                        return queries.entrySet().stream();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> sqlAnalyzer.analyze(entry.getValue())
                ));

        reportGenerator.generateReport(analysisResults);
    }
}