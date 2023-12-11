package org.developx.mybatisParser.domain.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.repository.MapperRepository;
import org.developx.mybatisParser.domain.mapper.service.MapperService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

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

    @Override
    public Page<Mapper> findMappers(String fullName) {
        return mapperRepository.findMappers(fullName, Pageable.ofSize(10));
    }

    @Override
    public Mapper findById(Long mapperId) {
        return mapperRepository.findById(mapperId)
                .orElseThrow(() -> new IllegalArgumentException("mapper id is long = " + mapperId));
    }
}
