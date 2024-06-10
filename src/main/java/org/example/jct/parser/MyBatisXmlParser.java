package org.example.jct.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyBatisXmlParser {
    public Map<String, String> parse(File file) throws Exception {
        Map<String, String> queries = new HashMap<>();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element root = document.getDocumentElement();

        String[] elements = {"select", "insert", "update", "delete"};
        for (String elementName : elements) {
            NodeList nodeList = root.getElementsByTagName(elementName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String id = element.getAttribute("id");
                String sql = element.getTextContent().trim();
                queries.put(id, sql);
            }
        }

        return queries;
    }
}
