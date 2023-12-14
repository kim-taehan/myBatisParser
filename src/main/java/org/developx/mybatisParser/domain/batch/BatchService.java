package org.developx.mybatisParser.domain.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.util.StringUtil;
import org.developx.mybatisParser.domain.analysis.sqlparser.SqlFacade;
import org.developx.mybatisParser.domain.analysis.sqlparser.SqlParserResult;
import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;
import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.xmlparser.XmlParser;
import org.developx.mybatisParser.domain.mapper.data.ElType;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.mapper.entity.Sql;
import org.developx.mybatisParser.domain.mapper.service.MapperService;
import org.developx.mybatisParser.domain.mapper.service.NamespaceService;
import org.developx.mybatisParser.domain.mapper.service.SqlService;
import org.developx.mybatisParser.domain.snapshot.entity.XmlFile;
import org.developx.mybatisParser.domain.snapshot.service.SnapshotService;
import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.developx.mybatisParser.domain.tables.service.ColService;
import org.developx.mybatisParser.domain.tables.service.TableService;
import org.dom4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BatchService {

    private final SnapshotService snapshotService;
    private final XmlParser xmlParser;
    private final NamespaceService namespaceService;
    private final MapperService mapperService;
    private final SqlService sqlService;
    private final SqlFacade sqlFacade;
    private final TableService tableService;
    private final ColService colService;

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
                MapperParseResult mapperParseResult = saveSql(element, mapper);
                saveTable(mapperParseResult, mapper);
            }
        }
    }

    private void saveTable(MapperParseResult mapperParseResult, Mapper mapper) {

        Map<String, Set<String>> tables = mapperParseResult.getTables();
        for (String tableName : tables.keySet()) {
            if(StringUtils.hasText(tableName)) {
                Tables table = tableService.findTableOrElse(tableName);
                for (String colName : tables.get(tableName)) {
                    Col save = colService.save(table, colName);
                }

                log.info(table.getTableName() + " : " + table.getCols().size());
            }
        }
    }

    private MapperParseResult saveSql(Element element, Mapper mapper) {

        String[] result = xmlParser.findSqlTexts(element);

        MapperParseResult mapperParseResult = new MapperParseResult();

        for (String text : result) {
            ParseResult parser = sqlFacade.parser(text);
            mapperParseResult.mergeTables(parser.getTables());
            Sql sql = Sql.builder()
                    .mapper(mapper)
                    .originQuery(text)
                    .parseResult(parser)
                    .build();
            sqlService.save(sql);
        }

        return mapperParseResult;
    }

    private static String getAttribute(Element element, String key) {
        Attribute attribute = element.attribute(key);
        return attribute == null ? "" : attribute.getValue();
    }


}
