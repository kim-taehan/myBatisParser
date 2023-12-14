package org.developx.mybatisParser.domain.tables.repository;

import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Tables, Long> {
    Optional<Tables> findByTableName(String tableName);
}
