package org.developx.mybatisParser.domain.analysis.xmlparser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[service] xml parser")
class XmlParserTest {


    XmlParser xmlParser;
    {
        try {
            xmlParser = new XmlParserImpl();
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("utf-8 인코딩된 xml mapper 조회할 수 있다.")
    @Test
    void parseDocumentUtf8() throws DocumentException {
        // given
        File file = new File("src/test/resources/xmlfiles/utf8_xml.xml");

        // when
        Document document = xmlParser.parseDocument(file);
        String namespace = document.getRootElement().attribute("namespace").getValue();

        // then
        assertThat(namespace).isEqualTo("org.developx.utf-8-dao");
    }

    @DisplayName("euc-kr 인코딩된 xml mapper 조회할 수 있다.")
    @Test
    void parseDocumentEucKr() throws DocumentException {
        // given
        File file = new File("src/test/resources/xmlfiles/eucKr_xml.xml");

        // when
        Document document = xmlParser.parseDocument(file);
        String namespace = document.getRootElement().attribute("namespace").getValue();

        // then
        assertThat(namespace).isEqualTo("org.developx.euc-kr-dao");
    }

    @DisplayName("<if> 구문은 조건을 만족할때와 안할떄 2개지 케이스를 2n 복잡도로 나타낸다.")
    @Test
    void findSqlTextsIfEl() throws DocumentException {
        // given
        Element element = getElement("if_element.xml");

        // when
        String[] sqlTexts = xmlParser.findSqlTexts(element);
        List<String> collect = removeBlank(sqlTexts);

        // then
        assertThat(collect).hasSize(4)
                .contains("", "사과", "바나나", "사과바나나");
    }

    @DisplayName("<choose> 구문은 when*n, otherwise 만큼 케이스가 늘어난다.")
    @Test
    void findSqlTextsChooseEl() throws DocumentException {
        // given
        Element element = getElement("choose_element.xml");

        // when
        String[] sqlTexts = xmlParser.findSqlTexts(element);
        List<String> collect = removeBlank(sqlTexts);

        // then
        assertThat(collect).hasSize(3)
                .contains("남성", "여성", "트랜스젠더");
    }

    @DisplayName("<foreach> 구문은 1건만으로 문법에만 맞게 변경된다.")
    @Test
    void findSqlTextsForeachEl() throws DocumentException {
        // given
        Element element = getElement("foreach_element.xml");

        // when
        String[] sqlTexts = xmlParser.findSqlTexts(element);
        List<String> collect = removeBlank(sqlTexts);

        // then
        assertThat(collect).hasSize(1)
                .contains("(내용)");
    }

    @DisplayName("<where> 구문은 안에 내용이 있는경우만 where 을 붙인다.")
    @Test
    void findSqlTextWhereEl() throws DocumentException {
        // given
        Element element = getElement("where_element.xml");

        // when
        String[] sqlTexts = xmlParser.findSqlTexts(element);
        List<String> collect = removeBlank(sqlTexts);

        // then
        assertThat(collect).hasSize(4)
                .contains("", "where사과", "where바나나", "where사과바나나");
    }

    @DisplayName("<set> 구문은 안에 내용이 있는경우만 set 을 붙인다.")
    @Test
    void findSqlTextSetEl() throws DocumentException {
        // given
        Element element = getElement("set_element.xml");

        // when
        String[] sqlTexts = xmlParser.findSqlTexts(element);
        List<String> collect = removeBlank(sqlTexts);

        // then
        assertThat(collect).hasSize(4)
                .contains("", "set사과,", "set바나나,", "set사과,바나나,");
    }


    private static List<String> removeBlank(String[] sqlTexts) {
        return Arrays.stream(sqlTexts)
                .map(text -> text.replaceAll("\\s", ""))
                .collect(Collectors.toList());
    }

    private Element getElement(String filename) throws DocumentException {
        File file = new File("src/test/resources/xmlfiles/" + filename);
        Document document = xmlParser.parseDocument(file);
        Iterator iterator = document.getRootElement().elementIterator();
        return (Element) iterator.next();
    }

}