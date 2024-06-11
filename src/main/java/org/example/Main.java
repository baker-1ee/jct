package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.data.Query;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String directory = "/Users/a452609/IdeaProjects/jct/src/main/resources/mock/mybatis";

        XmlFileExplorer fileExplorer = new XmlFileExplorer();
        MyBatisXmlParser xmlParser = new MyBatisXmlParser();
        SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
        ReportGenerator reportGenerator = new ReportGenerator();

        List<File> xmlFiles = fileExplorer.findXmlFiles(directory);
        Map<String, String> queries = xmlParser.parse(xmlFiles);

        List<Query> queryList = queries.entrySet()
                .stream()
                .map(entry -> Query.of(entry.getKey(), entry.getValue(), sqlAnalyzer.analyze(entry.getValue())))
                .collect(Collectors.toList());

        reportGenerator.generateReport(queryList);
    }
}