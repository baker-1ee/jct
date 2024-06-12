package org.example.jct.parser;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MyBatisXmlParser {

    public Map<String, String> parse(File file) {
        Map<String, String> queries = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // DTD 검증 비활성화
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    if (systemId.contains("mybatis-3-mapper.dtd")) {
                        InputStream dtdStream = getClass().getClassLoader().getResourceAsStream("dtd/mybatis-3-mapper.dtd");
                        return new InputSource(dtdStream);
                    } else if (systemId.contains("mybatis-3-config.dtd")) {
                        InputStream dtdStream = getClass().getClassLoader().getResourceAsStream("dtd/mybatis-3-config.dtd");
                        return new InputSource(dtdStream);
                    }
                    return null;
                }
            });
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();

            String[] elements = {"select", "insert", "update", "delete", "sql"};
            for (String elementName : elements) {
                NodeList nodeList = root.getElementsByTagName(elementName);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    String id = element.getAttribute("id");
                    String sql = element.getTextContent().trim();
                    queries.put(id, sql);
                }
            }
        } catch (Exception e) {
            log.error("MyBatisXmlParser error", e);
        }
        return queries;
    }
}
