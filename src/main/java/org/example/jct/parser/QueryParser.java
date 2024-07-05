package org.example.jct.parser;

import org.w3c.dom.Element;

import java.io.File;
import java.util.List;

public interface QueryParser {

    List<ParsedQuery> parse(List<File> files);

    List<Element> getElements(File file);

}
