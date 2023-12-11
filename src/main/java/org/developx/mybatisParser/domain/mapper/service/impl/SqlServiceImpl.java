package org.developx.mybatisParser.domain.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Sql;
import org.developx.mybatisParser.domain.mapper.repository.SqlRepository;
import org.developx.mybatisParser.domain.mapper.service.SqlService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqlServiceImpl implements SqlService {

    private final SqlRepository sqlRepository;

    @Override
    public void save(Sql sql) {
        sqlRepository.save(sql);
    }

    @Override
    public Page<Sql> findSqls(Mapper mapper) {

        return sqlRepository.findByMapper(mapper, Pageable.ofSize(10));
    }
}
