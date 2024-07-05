package org.example.jct.service;

import lombok.RequiredArgsConstructor;
import org.example.jct.analyzer.AnalyzedQuery;
import org.example.jct.analyzer.QueryAnalyzer;
import org.example.jct.converter.FileCopier;
import org.example.jct.converter.QueryConverter;
import org.example.jct.parser.FileExplorer;
import org.example.jct.parser.ParsedQuery;
import org.example.jct.parser.QueryParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrateService {

    private final FileExplorer fileExplorer;
    private final FileCopier fileCopier;
    private final QueryParser queryParser;
    private final QueryAnalyzer queryAnalyzer;
    private final QueryConverter queryConverter;

    /**
     * 쿼리 자동 변환
     */
    public void migrate(String srcDirPath) {
        // file copy
        List<File> files = fileExplorer.findFiles(srcDirPath);
        String tgtDirPath = getTargetDirPath(srcDirPath);
        fileCopier.copy(files, srcDirPath, tgtDirPath);

        // 자동 변환 가능 query 변환
        List<File> targetFiles = fileExplorer.findFiles(tgtDirPath);
        List<ParsedQuery> parsedQueries = queryParser.parse(targetFiles);
        List<AnalyzedQuery> targetQueryList = queryAnalyzer.analyze(parsedQueries);
        queryConverter.convert(targetFiles, targetQueryList);
    }

    private String getTargetDirPath(String srcDirPath) {
        Path srcPath = Paths.get(srcDirPath);
        Path parentPath = srcPath.getParent();
        Path tgtPath = parentPath.resolve("migration");
        return tgtPath.toString();
    }

}
