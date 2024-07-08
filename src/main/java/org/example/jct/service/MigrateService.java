package org.example.jct.service;

import lombok.RequiredArgsConstructor;
import org.example.jct.analyzer.AnalyzedQuery;
import org.example.jct.analyzer.QueryAnalyzer;
import org.example.jct.converter.QueryConverter;
import org.example.jct.parser.FileExplorer;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.parser.QueryParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final FileExplorer fileExplorer;
    private final QueryParser queryParser;
    private final QueryAnalyzer queryAnalyzer;
    private final QueryConverter queryConverter;

    /**
     * 쿼리 자동 변환
     */
    public void migrate(String rootDirPath) {
        List<File> files = fileExplorer.findFiles(rootDirPath);
        List<ParsedQuery> parsedQueries = queryParser.parse(files);
        List<AnalyzedQuery> queryList = queryAnalyzer.analyze(parsedQueries);
        queryConverter.convert(files, queryList);
    }

}
