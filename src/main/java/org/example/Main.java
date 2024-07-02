package org.example;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.converter.QueryConverter;
import org.example.jct.converter.XmlFileCopier;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ExcelReportGenerator;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String sourceDirectoryPath = getSourceDirectoryPath(args);
        List<File> xmlFiles = collectXmlFiles(sourceDirectoryPath);
        List<ParsedQuery> parsedQueryList = parseQuery(xmlFiles);
        List<OracleQuery> oracleQueryList = scanOracleKeyword(parsedQueryList);
        generateReport(oracleQueryList);

        // xml 파일 copy
        String targetDirectoryPath = getTargetDirectoryPath(args);
        if (targetDirectoryPath == null) return;

        XmlFileCopier xmlFileCopier = new XmlFileCopier(sourceDirectoryPath, targetDirectoryPath);
        xmlFileCopier.copy(xmlFiles);

        // 자동 변환 가능 query 변환
        List<File> targetXmlFiles = collectXmlFiles(targetDirectoryPath);
        List<ParsedQuery> parsedTargetQueryList = parseQuery(targetXmlFiles);
        List<OracleQuery> oracleTargetQueryList = scanOracleKeyword(parsedTargetQueryList);
        QueryConverter queryConverter = new QueryConverter();
        queryConverter.convert(targetXmlFiles, oracleTargetQueryList);
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
//        ReportGenerator reportGenerator = new ConsoleReportGenerator();
        ReportGenerator reportGenerator = new ExcelReportGenerator();
        reportGenerator.generateReport(queries);
    }

    // argument 검증
    private static String getSourceDirectoryPath(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -jar jct.jar {{source directory path}}");
            System.exit(1);
        }
        return args[0];
    }

    // argument 검증
    private static String getTargetDirectoryPath(String[] args) {
        return args.length == 2 ? args[1] : null;
    }
}