package org.developx.mybatisParser.domain.analysis.finder.filevisitor;

import lombok.extern.slf4j.Slf4j;
import org.developx.mybatisParser.global.properties.items.FileCheckProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

@Slf4j
public class XmlVisitor implements FileVisitor<Path> {

    private final FileCheckProperties fileCheckProperties;
    private final Collection<File> xmlFiles;

    public XmlVisitor(FileCheckProperties fileCheckProperties, Collection<File> xmlFiles) {
        this.fileCheckProperties = fileCheckProperties;
        this.xmlFiles = xmlFiles;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        String fileName = file.getFileName().toString();
        if(fileCheckProperties.checkedFileName(fileName)) {
            xmlFiles.add(file.toFile());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return fileCheckProperties.uncheckedDirPath(file) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
