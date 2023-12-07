package org.developx.mybatisParser.domain.analysis.finder.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.analysis.finder.filevisitor.XmlVisitor;
import org.developx.mybatisParser.domain.analysis.finder.DirectoryFinder;
import org.developx.mybatisParser.global.properties.items.FileCheckProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FileVisitorFinder implements DirectoryFinder {

    private final FileCheckProperties fileCheckProperties;

    @Override
    public Collection<File> findDirectory(String fullPath) {
        Collection<File> xmlFiles = new ArrayList<>();
        try {
            Files.walkFileTree(Path.of(fullPath), new XmlVisitor(fileCheckProperties, xmlFiles));
        } catch (IOException e) {
            throw new IllegalArgumentException("경로를 확인할 수 없습니다.");
        }
        return xmlFiles;
    }
}
