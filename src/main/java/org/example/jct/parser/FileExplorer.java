package org.example.jct.parser;

import java.io.File;
import java.util.List;

public interface FileExplorer {

    List<File> findFiles(String rootDirPath);

}
