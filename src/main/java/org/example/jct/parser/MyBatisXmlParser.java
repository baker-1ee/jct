package org.example.jct.parser;

import lombok.extern.slf4j.Slf4j;
import org.example.jct.data.ParsedQuery;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MyBatisXmlParser {

    private static final List<String> ELEMENT_TAG_NAMES = Arrays.asList("select", "insert", "update", "delete", "sql");

    /**
     * xml 파일에서 mybatis 구문 파싱하여 query 추출
     */
    public List<ParsedQuery> parse(List<File> xmlFiles) {
        return xmlFiles.stream()
                .map(this::parse)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<ParsedQuery> parse(File file) {
        List<ParsedQuery> queries = new ArrayList<>();
        try {
            Element rootElement = getElement(file);
            for (String tagName : ELEMENT_TAG_NAMES) {
                NodeList nodeList = rootElement.getElementsByTagName(tagName);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    queries.add(ParsedQuery.of(file, element));
                }
            }
        } catch (Exception e) {
            log.error("MyBatisXmlParser error", e);
        }
        return queries;
    }

    public List<Element> getElements(File file) {
        List<Element> elements = new ArrayList<>();
        try {
            Element rootElement = getElement(file);
            for (String tagName : ELEMENT_TAG_NAMES) {
                NodeList nodeList = rootElement.getElementsByTagName(tagName);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    elements.add(element);
                }
            }
        } catch (Exception e) {
            log.error("MyBatisXmlParser error", e);
        }
        return elements;
    }

    private Element getElement(File file) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder builder = getDocumentBuilder();
        Document document = builder.parse(file);
        return document.getDocumentElement();
    }

    private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // XML DTD 검증 비활성화 (폐쇄망에서 동작하려면 원격 dtd 파일이 아닌 로컬 dtd 파일로 검증해야하기 때문에)
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature("http://xml.org/sax/features/validation", false);
        // XML DTD 검증 시 로컬 dtd 파일 사용하도록 설정
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
        return builder;
    }

}
