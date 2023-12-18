package org.developx.mybatisParser.domain.tables.service;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.MapperCol;
import org.developx.mybatisParser.domain.tables.entity.MapperTables;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.developx.mybatisParser.domain.tables.repository.MapperColRepository;
import org.developx.mybatisParser.domain.tables.repository.MapperTableRepository;
import org.developx.mybatisParser.domain.tables.service.impl.MapperColService;
import org.developx.mybatisParser.domain.tables.service.impl.MapperTableService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperColServiceImpl implements MapperColService {

    private final MapperColRepository mapperColRepository;
    @Override
    public void save(Mapper mapper, Col col) {
        MapperCol mapperCol = MapperCol.builder().mapper(mapper).col(col).build();
        mapperColRepository.save(mapperCol);
    }
}
