package org.developx.mybatisParser.domain.mapper.repository.querydsl;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MapperRepositoryQueryDsl {
    Page<Mapper> findMappers(String fullName, Pageable pageable);

    Page<Mapper> findMappersByTables(String tableName, Pageable pageable);
    Page<Mapper> findMappersByCol(String tableName, String colName, Pageable pageable);
}
