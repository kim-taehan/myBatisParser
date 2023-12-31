package org.developx.mybatisParser.domain.mapper.service;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.springframework.data.domain.Page;

public interface MapperService {
    void save(Mapper mapper);

    Page<Mapper> findMappers(String fullName, String tableName, String colName);

    Mapper findById(Long mapperId);
}
