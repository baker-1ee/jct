package org.example.jct.parser;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MyBatisXmlParser {

    public Map<String, String> parse(List<File> files) {
        Map<String, String> queries = new HashMap<>();
        files.forEach(file -> {
            try {
                Map<String, String> fileQueries = parseFile(file);
                queries.putAll(fileQueries);
            } catch (Exception e) {
                log.error("Error parsing file: {}", file.getName(), e);
            }
        });
        return queries;
    }

    private Map<String, String> parseFile(File file) throws Exception {
        Map<String, String> queries = new HashMap<>();
        var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        var root = document.getDocumentElement();

        String[] elements = {"select", "insert", "update", "delete"};
        for (String elementName : elements) {
            NodeList nodeList = root.getElementsByTagName(elementName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    String sql = element.getTextContent().trim();
                    queries.put(id, sql);
                }
            }
        }

        return queries;
    }
}
