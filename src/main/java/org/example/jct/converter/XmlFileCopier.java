package org.example.jct.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class XmlFileCopier {
    /**
     * 쿼리 변환 작업 중일 수 있으므로 이미 target driectory 에 XML 파일이 존재하면 그대로 둔다.
     */
    public void copy(List<File> xmlFiles, String srcDirPath, String tgtDirPath) {
        try {
            Path srcDir = Paths.get(srcDirPath);
            Path tgtDir = Paths.get(tgtDirPath);

            Files.createDirectories(tgtDir);

            for (File xmlFile : xmlFiles) {
                Path sourcePath = xmlFile.toPath();
                Path targetPath = tgtDir.resolve(srcDir.relativize(sourcePath));
                Files.createDirectories(targetPath.getParent());

                if (!Files.exists(targetPath)) {
                    Files.copy(sourcePath, targetPath);
                } else {
                    log.info("File already exists, skipping: {}", targetPath);
                }
            }
        } catch (IOException e) {
            log.error("Query Converter error", e);
            throw new RuntimeException(e);
        }

    }
}
