package org.developx.mybatisParser.domain.tables.repository;

import org.developx.mybatisParser.domain.tables.entity.MapperTables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapperTableRepository extends JpaRepository<MapperTables, Long> {
}
