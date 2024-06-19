package org.example;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ConsoleReportGenerator;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String rootDirectoryPath = getRootDirectoryPath(args);
        List<File> xmlFiles = collectXmlFiles(rootDirectoryPath);
        List<ParsedQuery> parsedQueryList = parseQuery(xmlFiles);
        List<OracleQuery> oracleQueryList = scanOracleKeyword(parsedQueryList);
        generateReport(oracleQueryList);
    }

    // root directory 하위의 xml 파일 수집
    private static List<File> collectXmlFiles(String rootDirectory) {
        XmlFileExplorer fileExplorer = new XmlFileExplorer();
        return fileExplorer.findXmlFiles(rootDirectory);
    }

    // xml 파일에서 query 추출
    private static List<ParsedQuery> parseQuery(List<File> xmlFiles) {
        MyBatisXmlParser xmlParser = new MyBatisXmlParser();
        return xmlFiles.stream()
                .map(xmlParser::parse)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // 파싱된 쿼리에서 오라클 키워드 스캔
    private static List<OracleQuery> scanOracleKeyword(List<ParsedQuery> queries) {
        SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
        return queries.stream()
                .map(sqlAnalyzer::analyze)
                .collect(Collectors.toList());
    }

    // 분석 report 생성
    private static void generateReport(List<OracleQuery> queries) {
        ReportGenerator reportGenerator = new ConsoleReportGenerator();
//        ReportGenerator reportGenerator = new ExcelReportGenerator();
        reportGenerator.generateReport(queries);
    }

    // argument 검증
    private static String getRootDirectoryPath(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar jct.jar <root directory path>");
            System.exit(1);
        }
        return args[0];
    }
}