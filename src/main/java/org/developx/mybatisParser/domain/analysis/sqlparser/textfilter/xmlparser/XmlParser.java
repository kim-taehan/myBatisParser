package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.xmlparser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.File;

public interface XmlParser {
    Document parseDocument(File file) throws DocumentException;

    String[] findSqlTexts(Element element);
}
