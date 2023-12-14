package org.developx.mybatisParser.domain.tables.service;

import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.tables.entity.Tables;

public interface TableService {
    void save(Tables table);

    Tables findTableOrElse(String tableName);
}
