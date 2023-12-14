package org.developx.mybatisParser.domain.tables.service;

import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.Tables;

public interface ColService {

    void save(Col col);

    Col save(Tables table, String colName);
}
