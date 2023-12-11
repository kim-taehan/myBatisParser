package org.developx.mybatisParser.domain.mapper.service;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Sql;
import org.springframework.data.domain.Page;

public interface SqlService {
    void save(Sql sql);

    Page<Sql> findSqls(Mapper mapper);
}
