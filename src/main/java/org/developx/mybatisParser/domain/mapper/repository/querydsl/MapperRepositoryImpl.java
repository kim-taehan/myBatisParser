package org.developx.mybatisParser.domain.mapper.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.tables.entity.QCol;
import org.developx.mybatisParser.domain.tables.entity.QMapperCol;
import org.developx.mybatisParser.domain.tables.entity.QTables;
import org.developx.mybatisParser.global.infra.jpa.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import static org.developx.mybatisParser.domain.mapper.entity.QMapper.mapper;
import static org.developx.mybatisParser.domain.tables.entity.QCol.col;
import static org.developx.mybatisParser.domain.tables.entity.QMapperCol.mapperCol;
import static org.developx.mybatisParser.domain.tables.entity.QMapperTables.mapperTables;
import static org.developx.mybatisParser.domain.tables.entity.QTables.tables;

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

    public Page<Mapper> findMappersByTables(String tableName, Pageable pageable) {
        return applyPagination(pageable,
                contentQuery -> contentQuery
                        .select(mapper)
                        .from(tables)
                        .leftJoin(mapperTables).on(tables.eq(mapperTables.table))
                        .leftJoin(mapper).on(mapper.eq(mapperTables.mapper))
                        .where(tables.tableName.eq(tableName.toLowerCase())
                        )
        );
    }

    /*
        select *
        from tb_col col
        , tb_mapper_col mc
        , tb_tables tables
        , tb_mapper mapper
        where col.col_name = 'b3'
        and col.col_id = mc.col_id
        and col.table_id = tables.table_id
        and tables.table_name = 'bb'
        and mapper.mapper_id = mc.mapper_id
     */
    @Override
    public Page<Mapper> findMappersByCol(String tableName, String colName, Pageable pageable) {
        return applyPagination(pageable,
                contentQuery -> contentQuery
                        .select(mapper)
                        .from(col)
                        .leftJoin(mapperCol).on(col.eq(mapperCol.col))
                        .leftJoin(mapper).on(mapper.eq(mapperCol.mapper))
                        .where(col.colName.eq(colName.toLowerCase())
                                , tableNameEq(tableName)
                        )
        );
    }

    private static BooleanExpression tableNameEq(String tableName) {
        return StringUtils.hasText(tableName) ? col.table.tableName.eq(tableName.toLowerCase()) : null;
    }
}
