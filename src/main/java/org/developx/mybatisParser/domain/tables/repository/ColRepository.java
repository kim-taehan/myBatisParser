package org.developx.mybatisParser.domain.tables.repository;

import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColRepository extends JpaRepository<Col, Long> {
    Optional<Col> findByTableAndColName(Tables table, String colName);
}
