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
//        String[] result = extractElement(element, new String[]{""});
        for (String text : result) {
            log.info("-----------------------------------------------------------------");
            log.info(text);
        }
    }

    private String[] extractElement(Element element, String[] origin) {
        String[] ret = origin.clone();
        for (Iterator i = element.nodeIterator(); i.hasNext(); ) {
            Node node = (Node) i.next();
            short nodeType = node.getNodeType();
            if (nodeType == Node.TEXT_NODE) {
                String text = StringUtils.hasText(node.getText().trim()) ? node.getText() : " ";
                ret = multiSql(ret, new String[]{text});
            }
            else if (nodeType == Node.ELEMENT_NODE) {
                DefaultElement defaultElement = (DefaultElement) node;
                String name = defaultElement.getName();
                if("if".equals(name)) {
                    String[] additional = extractElement(defaultElement, ret);
                    ret = subSql(ret, additional);
                }
                else if("choose".equals(name)) {
                    ret = extractChooseElement(defaultElement, ret);
                }
                else if("when".equals(name) || "otherwise".equals(name)) {
                    ret = multiSql(ret, extractElement(defaultElement, ret));
                }
                else if("where".equals(name) || "set".equals(name)) {
                    ret = extractWhereElement(defaultElement, ret, name);
                }
                else if ("foreach".equals(name)) {
                    ret = extractForeachElement(defaultElement, ret);
                }
                else if ("trim".equals(name)) {
                    ret = extractTrimElement(defaultElement, ret);
                }

            }
        }
        return ret;
    }

    private String[] extractTrimElement(DefaultElement element, String[] origin) {
        String prefix = getAttribute(element, "prefix");
        String suffix = getAttribute(element, "suffix");


        String[] prefixOverrides = getAttribute(element, "prefixOverrides").split("\\|");
        String[] suffixOverrides = getAttribute(element, "suffixOverrides").split("\\|");


        String[] ret = extractElement(element, new String[0]);
        for (int i = 0; i < ret.length; i++) {
            String string = ret[i].trim();
            for (String prefixOverride : prefixOverrides) {
                if (StringUtils.hasText(prefixOverride) && string.startsWith(prefixOverride.trim())) {
                    string = string.substring(prefixOverride.length()-1, string.length());
                    break;
                }
            }
            for (String suffixOverride : suffixOverrides) {
                if (StringUtils.hasText(suffixOverride) && string.endsWith(suffixOverride.trim())) {
                    string = string.substring(0, string.lastIndexOf(suffixOverride.trim()));
                    break;
                }
            }

            ret[i] = " " + string + " ";
        }


        ret = multiSql(
                new String[]{String.format(" %s ", prefix)},
                ret
        );
        ret = multiSql(
                ret,
                new String[]{String.format(" %s ", suffix)}
        );


        return multiSql(origin, ret);
    }

    private String[] extractForeachElement(Element element, String[] origin){
        String open = getAttribute(element, "open");
        String close = getAttribute(element, "close");

        String[] strings = multiSql(
                new String[]{String.format(" %s ", open)},
                extractElement(element, new String[0])
        );
        return multiSql(
                strings,
                new String[]{String.format(" %s ", close)}
        );

    }
    private String[] extractWhereElement(Element element, String[] origin, String prefix){
        String[] strings = extractElement(element, new String[]{" "});

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if (StringUtils.hasText(string.trim())) {
                strings[i] = String.format("\n%s\n%s", prefix, string);
            }
        }



        return multiSql(origin, strings);
    }

    private String[] extractChooseElement(Element element, String[] origin){

        String[] ret = new String[]{};
        for (Iterator i = element.nodeIterator(); i.hasNext();){
            Node node = (Node) i.next();
            short nodeType = node.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                String[] strings = extractElement((DefaultElement) node, new String[]{" "});
                ret = subSql(ret, strings);
            }
        }
        return multiSql(origin, ret);
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


    private static String getAttribute(Element element, String key) {
        Attribute attribute = element.attribute(key);
        return attribute == null ? "" : attribute.getValue();
    }


}
