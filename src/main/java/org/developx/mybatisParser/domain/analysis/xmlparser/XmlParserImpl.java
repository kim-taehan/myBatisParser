package org.developx.mybatisParser.domain.analysis.xmlparser;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.Iterator;

import static org.dom4j.Node.*;

@Component
@Slf4j
public class XmlParserImpl implements XmlParser {

    private static final String XML_PARSER_URL = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    private final SAXReader utf8Reader;
    private final SAXReader eucKrReader;

    public XmlParserImpl() throws SAXException {
        this.utf8Reader = getSaxReader( "UTF-8");
        this.eucKrReader = getSaxReader( "EUC-KR");
    }

    private SAXReader getSaxReader(String encoding) throws SAXException {
        SAXReader reader = new SAXReader(false);
        reader.setEncoding(encoding);
        reader.setFeature(XML_PARSER_URL, false);
        return reader;
    }

    public Document parseDocument(File file) throws DocumentException {
        try {
            return this.utf8Reader.read(file);
        } catch (DocumentException e) {
            return this.eucKrReader.read(file);
        }
    }

    private final String[] EMPTY_ARRAY = new String[0];
    private final String[] BLANK_ARRAY = new String[]{" "};

    @Override
    public String[] findSqlTexts(Element element) {
        return extractElement(element, EMPTY_ARRAY);
    }

    private String[] extractElement(Element element, String[] origin) {

        String[] ret = origin.clone();
        for (Iterator i = element.nodeIterator(); i.hasNext(); ) {
            Node node = (Node) i.next();
            ret = extractNode(node, ret);
        }

        return ret;
    }

    private String[] extractNode(Node node, String[] origin) {
        return switch (node.getNodeType()) {
            case TEXT_NODE -> extractTextNode(node, origin);
            case COMMENT_NODE -> origin;
            case ELEMENT_NODE -> extractElementNode(node, origin);
            default -> origin;
        };
    }

    private String[] extractTextNode(Node node, String[] origin) {
        String text = hasTextNode(node.getText()) ? node.getText() : " ";
        return multiSql(origin, new String[]{ text });
    }

    private boolean hasTextNode(String text) {
        return StringUtils.hasText(text.trim());
    }

    private String[] extractElementNode(Node node, String[] origin) {
        DefaultElement defaultElement = (DefaultElement) node;
        String name = defaultElement.getName();

        return switch (name) {
            case "if" -> extractIfElement(defaultElement, origin);
            case "choose" -> extractChooseElement(defaultElement, origin);
            case "when", "otherwise" -> extractWhenElement(defaultElement, origin);
            case "foreach" -> extractForeachElement(defaultElement, origin);
            case "where", "set" -> extractWhereElement(defaultElement, origin, name);
            case "trim" -> extractTrimElement(defaultElement, origin);
            default -> origin;
//            default -> throw new IllegalArgumentException();
        };
    }

    private String[] extractTrimElement(DefaultElement defaultElement, String[] origin) {
        String prefix = getAttribute(defaultElement, "prefix");
        String suffix = getAttribute(defaultElement, "suffix");


        return origin;
    }



    private String[] extractIfElement(DefaultElement defaultElement, String[] origin) {
        // if 문 반영한것과 안한 케이스로 나눈다.
        String[] additional = extractElement(defaultElement, origin);
        return subSql(origin, additional);
    }

    private String[] extractChooseElement(DefaultElement defaultElement, String[] origin) {
        // choose 는 when, otherwise 숫자만큼 sql이 분할한다.
        String[] ret = new String[0];
        for (Iterator i = defaultElement.nodeIterator(); i.hasNext();){
            Node node = (Node) i.next();
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String[] whenText = extractElement((DefaultElement) node, new String[]{" "});
                ret = subSql(ret, whenText);
            }
        }
        return multiSql(origin, ret);
    }

    private String[] extractWhenElement(DefaultElement defaultElement, String[] origin) {
        return multiSql(origin, extractElement(defaultElement, origin));
    }

    private String[] extractForeachElement(DefaultElement defaultElement, String[] origin) {
        String open = getAttribute(defaultElement, "open");
        String close = getAttribute(defaultElement, "close");

        return multiSql(
                new String[]{String.format(" %s ", open)},
                extractElement(defaultElement, new String[0]),
                new String[]{String.format(" %s ", close)}
        );
    }

    private String[] extractWhereElement(DefaultElement defaultElement, String[] origin, String prefix) {
        String[] whereSql = extractElement(defaultElement, BLANK_ARRAY);
        for (int i = 0; i < whereSql.length; i++) {
            String string = whereSql[i];
            if (StringUtils.hasText(string.trim())) {
                whereSql[i] = String.format("\n%s\n%s", prefix, string);
            }
        }
        return multiSql(origin, whereSql);
    }

    private String[] subSql(String[] origin, String[] addition) {
        int size = origin.length + addition.length;
        String[] ret = new String[size];
        int k = 0;
        for (String text : origin) {
            ret[k++] = text;
        }
        for (String text : addition) {
            ret[k++] = text;
        }
        return ret;

    }

    private String[] multiSql(String[] origin, String[] addition) {
        if(addition.length == 0) return origin;
        if(origin.length == 0) return addition;
        int size = origin.length > addition.length ? origin.length : addition.length;
        String[] ret = new String[size];
        for (int i = 0; i < size; i++) {
            ret[i] = origin[i % origin.length] + addition[i % addition.length];
        }
        return ret;
    }

    private String[] multiSql(String[]... sqls) {
        String[] ret = EMPTY_ARRAY;
        for (String[] sql : sqls) {
            ret = multiSql(ret, sql);
        }
        return ret;
    }


    private static String getAttribute(Element element, String key) {
        Attribute attribute = element.attribute(key);
        return attribute == null ? "" : attribute.getValue();
    }
}
