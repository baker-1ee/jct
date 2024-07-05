package org.example.jct.parser.impl;

import org.example.jct.parser.FileExplorer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class XmlFileExplorer implements FileExplorer {

    @Override
    public List<File> findFiles(String rootDirPath) {
        File dir = new File(rootDirPath);
        List<File> xmlFiles = new ArrayList<>();
        findXmlFilesRecursively(dir, xmlFiles);
        return xmlFiles;
    }

    private void findXmlFilesRecursively(File dir, List<File> xmlFiles) {
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isDirectory()) {
                    findXmlFilesRecursively(file, xmlFiles);
                } else if (file.getName().endsWith(".xml")) {
                    xmlFiles.add(file);
                }
            }
        }
    }
}
