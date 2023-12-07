package org.developx.mybatisParser.domain.snapshot.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.snapshot.entity.Snapshot;
import org.developx.mybatisParser.domain.snapshot.entity.XmlFile;
import org.developx.mybatisParser.domain.snapshot.repository.SnapshotRepository;
import org.developx.mybatisParser.domain.snapshot.repository.XmlFileRepository;
import org.developx.mybatisParser.domain.snapshot.service.SnapshotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SnapshotServiceImpl implements SnapshotService {

    private final SnapshotRepository snapshotRepository;
    private final XmlFileRepository xmlFileRepository;

    @Override
    @Transactional
    public Long createSnapshot(Collection<File> xmlFiles) {

        Snapshot saveSnapshot = Snapshot.builder().xmlFileSize(xmlFiles.size()).build();
        snapshotRepository.save(saveSnapshot);

        List<XmlFile> xmls = xmlFiles.stream()
                .map(xmlFile -> XmlFile.builder().snapshot(saveSnapshot).file(xmlFile).build())
                .collect(Collectors.toList());
        xmlFileRepository.saveAll(xmls);

        return saveSnapshot.getId();

    }

    @Override
    public List<XmlFile> findXmlFiles(Long snapshotId) {
        return xmlFileRepository.findBySnapshotId(snapshotId);
    }
}
