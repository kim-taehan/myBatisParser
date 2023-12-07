package org.developx.mybatisParser.domain.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.developx.mybatisParser.domain.analysis.xmlparser.XmlParser;
import org.developx.mybatisParser.domain.analysis.xmlparser.XmlParserImpl;
import org.developx.mybatisParser.domain.mapper.data.ElType;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.mapper.service.MapperService;
import org.developx.mybatisParser.domain.mapper.service.NamespaceService;
import org.developx.mybatisParser.domain.snapshot.entity.XmlFile;
import org.developx.mybatisParser.domain.snapshot.service.SnapshotService;
import org.dom4j.*;
import org.dom4j.tree.DefaultElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BatchService {

    private final SnapshotService snapshotService;
    private final XmlParser xmlParser;
    private final NamespaceService namespaceService;
    private final MapperService mapperService;

    public void startBatch(Long snapshotId) {

        List<XmlFile> xmlFiles = snapshotService.findXmlFiles(snapshotId);

        for (XmlFile xmlFile : xmlFiles) {
            String absolutePath = xmlFile.getAbsolutePath();
            try {
                Document document = xmlParser.parseDocument(new File(absolutePath));
                xmlFile.success();

                savaNamespace(document);
            } catch (DocumentException e) {
                xmlFile.fail();
            }
        }
    }


    private void savaNamespace(Document document) {

        // namespace
        Element rootElement = document.getRootElement();
        Namespace namespace = Namespace.builder()
                .namespace(getAttribute(rootElement, "namespace"))
                .build();
        namespaceService.save(namespace);

        saveMapper(rootElement, namespace);
    }

    private void saveMapper(Element rootElement, Namespace namespace) {
        for (Iterator i = rootElement.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            if (element.getNodeType() == Node.ELEMENT_NODE) {
                Mapper mapper = Mapper.builder()
                        .elType(ElType.valueOfCustom(element.getName()))
                        .namespace(namespace)
                        .mapperName(getAttribute(element, "id"))
                        .xml(element.asXML())
                        .build();
                mapperService.save(mapper);

                saveSql(element, mapper);
            }
        }
    }

    private void saveSql(Element element, Mapper mapper) {

        String[] result = xmlParser.findSqlTexts(element);
        for (String text : result) {
            log.info("-----------------------------------------------------------------");
            log.info(text);
        }
    }

    private static String getAttribute(Element element, String key) {
        Attribute attribute = element.attribute(key);
        return attribute == null ? "" : attribute.getValue();
    }


}
