package org.developx.mybatisParser.domain.snapshot.service;

import org.developx.mybatisParser.domain.snapshot.entity.XmlFile;

import java.io.File;
import java.util.Collection;
import java.util.List;

public interface SnapshotService {
    Long createSnapshot(Collection<File> xmlFiles);

    List<XmlFile> findXmlFiles(Long id);
}
