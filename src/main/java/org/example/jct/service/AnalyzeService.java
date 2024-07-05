package org.example.jct.service;

import lombok.RequiredArgsConstructor;
import org.example.jct.analyzer.AnalyzedQuery;
import org.example.jct.analyzer.QueryAnalyzer;
import org.example.jct.parser.FileExplorer;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.parser.QueryParser;
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

    private final FileExplorer fileExplorer;
    private final QueryParser queryParser;
    private final QueryAnalyzer queryAnalyzer;
    private final ReportGenerator reportGenerator;

    /**
     * root directory 하위의 모든 xml 파일의 query 분석
     */
    public void analyze(String rootDirPath) {
        List<File> files = fileExplorer.findFiles(rootDirPath);
        List<ParsedQuery> parsedQueries = queryParser.parse(files);
        List<AnalyzedQuery> analyzedQueryList = queryAnalyzer.analyze(parsedQueries);
        reportGenerator.generate(analyzedQueryList);
    }

}
