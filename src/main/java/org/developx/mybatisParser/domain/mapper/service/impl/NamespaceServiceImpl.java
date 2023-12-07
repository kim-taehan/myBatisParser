package org.developx.mybatisParser.domain.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.mapper.repository.NamespaceRepository;
import org.developx.mybatisParser.domain.mapper.service.NamespaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NamespaceServiceImpl implements NamespaceService {

    private final NamespaceRepository namespaceRepository;

    @Override
    @Transactional
    public void save(Namespace namespace) {
        namespaceRepository.save(namespace);
    }
}
