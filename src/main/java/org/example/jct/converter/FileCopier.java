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
public class FileCopier {
    
    public void copy(List<File> files, String srcDirPath, String tgtDirPath) {
        try {
            Path srcDir = Paths.get(srcDirPath);
            Path tgtDir = Paths.get(tgtDirPath);

            Files.createDirectories(tgtDir);

            for (File xmlFile : files) {
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
