package org.example.jct;

import org.example.jct.analyzer.SqlAnalyzer;
import org.example.jct.converter.QueryConverter;
import org.example.jct.converter.XmlFileCopier;
import org.example.jct.data.OracleQuery;
import org.example.jct.data.ParsedQuery;
import org.example.jct.parser.MyBatisXmlParser;
import org.example.jct.parser.XmlFileExplorer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MigrateService {

    private final XmlFileExplorer fileExplorer = new XmlFileExplorer();
    private final XmlFileCopier fileCopier = new XmlFileCopier();
    private final MyBatisXmlParser xmlParser = new MyBatisXmlParser();
    private final SqlAnalyzer sqlAnalyzer = new SqlAnalyzer();
    private final QueryConverter queryConverter = new QueryConverter();

    /**
     * 쿼리 자동 변환
     */
    public void migrate(String srcDirPath) {
        // file copy
        List<File> xmlFiles = fileExplorer.findXmlFiles(srcDirPath);
        String tgtDirPath = getTargetDirPath(srcDirPath);
        fileCopier.copy(xmlFiles, srcDirPath, tgtDirPath);

        // 자동 변환 가능 query 변환
        List<File> targetXmlFiles = fileExplorer.findXmlFiles(tgtDirPath);
        List<ParsedQuery> parsedOracleQueries = xmlParser.parse(targetXmlFiles);
        List<OracleQuery> oracleTargetQueryList = sqlAnalyzer.analyze(parsedOracleQueries);
        queryConverter.convert(targetXmlFiles, oracleTargetQueryList);
    }

    private String getTargetDirPath(String srcDirPath) {
        Path srcPath = Paths.get(srcDirPath);
        Path parentPath = srcPath.getParent();
        Path tgtPath = parentPath.resolve("migration");
        return tgtPath.toString();
    }

}
