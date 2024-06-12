package org.example;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.data.Query;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ExcelReportGenerator;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar jct.jar <root directory path>");
            System.exit(1);
        }
        String directory = args[0];

        XmlFileExplorer fileExplorer = new XmlFileExplorer();
        MyBatisXmlParser xmlParser = new MyBatisXmlParser();
        SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
        ReportGenerator reportGenerator = new ExcelReportGenerator();

        List<File> xmlFiles = fileExplorer.findXmlFiles(directory);

        List<Query> queryList = xmlFiles.stream()
                .flatMap(file -> {
                    Map<String, String> queries = xmlParser.parse(file);
                    return queries.entrySet().stream()
                            .map(entry -> Query.of(
                                    file.getName(), entry.getKey(), entry.getValue(),
                                    sqlAnalyzer.analyze(entry.getValue())
                            ));
                })
                .collect(Collectors.toList());

        reportGenerator.generateReport(queryList);
    }
}