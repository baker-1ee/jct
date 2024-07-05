package org.example.jct.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

                // 파일을 UTF-8 인코딩으로 읽고 쓰기
                String content = new String(Files.readAllBytes(targetPath), StandardCharsets.UTF_8);
                Files.write(targetPath, content.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.error("File Copier error", e);
            throw new RuntimeException(e);
        }
    }
}
