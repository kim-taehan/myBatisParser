package org.developx.mybatisParser.domain.mapper.repository;

import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamespaceRepository extends JpaRepository<Namespace, Long> {

//    Optional<Mapper> findByFileNameAndNamespace(String fileName, String namespace);
}
