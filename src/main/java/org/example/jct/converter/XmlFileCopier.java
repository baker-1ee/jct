package org.example.jct.converter;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class XmlFileCopier {
    private final Path sourceDirectory;
    private final Path targetDirectory;

    public XmlFileCopier(String sourceDirectoryPath, String targetDirectoryPath) {
        sourceDirectory = Paths.get(sourceDirectoryPath);
        targetDirectory = Paths.get(targetDirectoryPath);
    }

    /**
     * 쿼리 변환 작업 중일 수 있으므로 이미 target driectory 에 XML 파일이 존재하면 그대로 둔다.
     */
    public void copy(List<File> xmlFiles) {
        try {
            Files.createDirectories(targetDirectory);

            for (File xmlFile : xmlFiles) {
                Path sourcePath = xmlFile.toPath();
                Path targetPath = targetDirectory.resolve(sourceDirectory.relativize(sourcePath));
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
