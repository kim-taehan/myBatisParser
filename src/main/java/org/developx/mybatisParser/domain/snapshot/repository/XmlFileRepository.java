package org.developx.mybatisParser.domain.snapshot.repository;

import org.developx.mybatisParser.domain.snapshot.entity.XmlFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XmlFileRepository extends JpaRepository<XmlFile, Long> {

    List<XmlFile> findBySnapshotId(Long snapshotId);
}
