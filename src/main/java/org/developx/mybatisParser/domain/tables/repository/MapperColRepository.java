package org.developx.mybatisParser.domain.tables.repository;

import org.developx.mybatisParser.domain.tables.entity.MapperCol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapperColRepository extends JpaRepository<MapperCol, Long> {
}
