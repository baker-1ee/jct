package org.example.jct;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ExcelReportGenerator;
import org.example.jct.report.ReportGenerator;

import java.io.File;
import java.util.List;

/**
 * mybatis xml 파일에서 source query 를 파싱하여 target query 로 변환하기 위한 분석 레포트 생성 서비스
 */
public class AnalyzeService {

    private final XmlFileExplorer fileExplorer = new XmlFileExplorer();
    private final MyBatisXmlParser xmlParser = new MyBatisXmlParser();
    private final SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
    private final ReportGenerator reportGenerator = new ExcelReportGenerator();

    /**
     * root directory 하위의 모든 xml 파일의 query 분석
     */
    public void analyze(String rootDirPath) {
        List<File> xmlFiles = fileExplorer.findXmlFiles(rootDirPath);
        List<ParsedQuery> parsedQueries = xmlParser.parse(xmlFiles);
        List<OracleQuery> oracleQueryList = sqlAnalyzer.analyze(parsedQueries);
        reportGenerator.generateReport(oracleQueryList);
    }
    
}
