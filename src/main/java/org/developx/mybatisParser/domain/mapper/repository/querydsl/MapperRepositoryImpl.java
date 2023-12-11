package org.developx.mybatisParser.domain.mapper.repository.querydsl;

import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.global.infra.jpa.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.developx.mybatisParser.domain.mapper.entity.QMapper.*;

public class MapperRepositoryImpl extends Querydsl4RepositorySupport implements MapperRepositoryQueryDsl {

    public MapperRepositoryImpl() {
        super(Mapper.class);
    }

    @Override
    public Page<Mapper> findMappers(String fullName, Pageable pageable) {
        return applyPagination(pageable,
                contentQuery -> contentQuery
                        .selectFrom(mapper)
                        .where(mapper.fullName.like("%" + fullName + "%"))
        );
    }
}
