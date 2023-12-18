package org.developx.mybatisParser.domain.tables.service.impl;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.Tables;

public interface MapperColService {
    void save(Mapper mapper, Col col);
}
