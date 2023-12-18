package org.developx.mybatisParser.domain.tables.service.impl;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.tables.entity.Tables;

public interface MapperTableService {
    void save(Mapper mapper, Tables tables);
}
