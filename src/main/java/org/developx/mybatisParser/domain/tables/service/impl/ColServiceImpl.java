package org.developx.mybatisParser.domain.tables.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.developx.mybatisParser.domain.tables.repository.ColRepository;
import org.developx.mybatisParser.domain.tables.repository.TableRepository;
import org.developx.mybatisParser.domain.tables.service.ColService;
import org.developx.mybatisParser.domain.tables.service.TableService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColServiceImpl implements ColService {

    private final ColRepository colRepository;


    @Override
    public void save(Col col) {
        colRepository.save(col);
    }

    @Override
    public Col save(Tables table, String colName) {
        return colRepository.findByTableAndColName(table, colName)
                .orElseGet(() -> createCol(table, colName));
    }

    private static Col createCol(Tables table, String colName) {
        Col saveCol = Col.builder().colName(colName).build();
        saveCol.setTable(table);
        return saveCol;
    }
}
