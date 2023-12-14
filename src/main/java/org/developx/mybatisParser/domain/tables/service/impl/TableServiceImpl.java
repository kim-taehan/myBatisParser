package org.developx.mybatisParser.domain.tables.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.developx.mybatisParser.domain.tables.repository.TableRepository;
import org.developx.mybatisParser.domain.tables.service.TableService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    @Override
    public void save(Tables table) {
        tableRepository.save(table);
    }

    @Override
    public Tables findTableOrElse(String tableName) {
        Tables findTable = tableRepository.findByTableName(tableName)
                .orElseGet(() -> createTable(tableName));
        return findTable;
    }

    private Tables createTable(String tableName) {
        Tables build = Tables.builder()
                .tableName(tableName)
                .build();
        return tableRepository.save(build);
    }
}
