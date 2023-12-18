package org.developx.mybatisParser.domain.tables.service;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.tables.entity.MapperTables;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.developx.mybatisParser.domain.tables.repository.MapperTableRepository;
import org.developx.mybatisParser.domain.tables.service.impl.MapperTableService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperTableServiceImpl implements MapperTableService {

    private final MapperTableRepository mapperTableRepository;
    @Override
    public void save(Mapper mapper, Tables tables) {
        MapperTables mapperTables = MapperTables.builder().mapper(mapper).table(tables).build();
        mapperTableRepository.save(mapperTables);
    }
}
