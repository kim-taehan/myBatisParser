package org.developx.mybatisParser.domain.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.mapper.repository.MapperRepository;
import org.developx.mybatisParser.domain.mapper.repository.NamespaceRepository;
import org.developx.mybatisParser.domain.mapper.service.MapperService;
import org.developx.mybatisParser.domain.mapper.service.NamespaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapperServiceImpl implements MapperService {

    private final MapperRepository mapperRepository;

    @Override
    @Transactional
    public void save(Mapper mapper) {
        mapperRepository.save(mapper);
    }
}