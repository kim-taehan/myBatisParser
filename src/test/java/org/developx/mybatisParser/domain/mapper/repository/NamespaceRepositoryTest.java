package org.developx.mybatisParser.domain.mapper.repository;

import org.developx.mybatisParser.DataJpaTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[repository] mapper repository")
class NamespaceRepositoryTest extends DataJpaTestSupport {

    @Autowired
    private NamespaceRepository namespaceRepository;

    @DisplayName("파일이름과 namespace로 mapper 정보를 조회할 수 있다.")
    @Test
    void findByFileNameAndNamespace(){
        // given
//        Mapper borderDao = Mapper.builder().fileName("test.xml").namespace("BorderDAO").build();
//        Mapper codeDao = Mapper.builder().fileName("test.xml").namespace("CodeDAO").build();
//
//        persist(borderDao, codeDao);
//
//        // when
//        Mapper findMapper = mapperRepository.findByFileNameAndNamespace(borderDao.getFileName(), borderDao.getNamespace())
//                .orElseThrow(() -> new IllegalStateException());
//
//        // then
//        assertThat(findMapper).isNotNull().extracting("fileName", "namespace")
//                .contains(borderDao.getFileName(), borderDao.getNamespace());
    }

}