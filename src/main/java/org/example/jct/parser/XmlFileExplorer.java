package org.example.jct.parser;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class XmlFileExplorer {

    public List<File> findXmlFiles(String directory) {
        File dir = new File(directory);
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
