package org.developx.mybatisParser.domain.snapshot.repository;

import org.developx.mybatisParser.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends JpaRepository<Snapshot, Long> {
}
