package org.example.jct;

import lombok.RequiredArgsConstructor;
import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;
import org.example.jct.report.ReportGenerator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * mybatis xml 파일에서 source query 를 파싱하여 target query 로 변환하기 위한 분석 레포트 생성 서비스
 */
@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final XmlFileExplorer fileExplorer;
    private final MyBatisXmlParser xmlParser;
    private final SqlAnalyzer sqlAnalyzer;
    private final ReportGenerator reportGenerator;

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
