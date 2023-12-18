package org.developx.mybatisParser.domain.mapper.repository.querydsl;

import org.assertj.core.groups.Tuple;
import org.developx.mybatisParser.DataJpaTestSupport;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;
import org.developx.mybatisParser.domain.mapper.entity.Namespace;
import org.developx.mybatisParser.domain.mapper.repository.MapperRepository;
import org.developx.mybatisParser.domain.tables.entity.Col;
import org.developx.mybatisParser.domain.tables.entity.MapperCol;
import org.developx.mybatisParser.domain.tables.entity.MapperTables;
import org.developx.mybatisParser.domain.tables.entity.Tables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.developx.mybatisParser.domain.mapper.data.ElType.SELECT;


@DisplayName("[querydsl] mapper querydsl")
class MapperRepositoryQueryDslTest extends DataJpaTestSupport {

    @Autowired
    MapperRepository mapperRepository;


    @Test
    @DisplayName("특정테이블을 사용하는 mapper를 조회할 수 있다.")
    void findMappersByTables() {

        // given
        Tables tableA = Tables.builder().tableName("tablea").build();
        Tables tableB = Tables.builder().tableName("tableb").build();

        Namespace namespace = Namespace.builder().namespace("namespaceA").build();
        Mapper mapperA = Mapper.builder().mapperName("mapperA").elType(SELECT).namespace(namespace).build();
        Mapper mapperB = Mapper.builder().mapperName("mapperB").elType(SELECT).namespace(namespace).build();
        Mapper mapperC = Mapper.builder().mapperName("mapperC").elType(SELECT).namespace(namespace).build();


        persist(tableA, tableB, namespace, mapperA, mapperB, mapperC,
                MapperTables.builder().table(tableA).mapper(mapperA).build(),
                MapperTables.builder().table(tableB).mapper(mapperB).build(),
                MapperTables.builder().table(tableA).mapper(mapperC).build(),
                MapperTables.builder().table(tableB).mapper(mapperC).build()
        );

        // when
        Page<Mapper> findMappers = mapperRepository.findMappersByTables("tableA", Pageable.ofSize(10));

        // then
        assertThat(findMappers).hasSize(2)
                .extracting("mapperName", "elType")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("mapperA", SELECT),
                        Tuple.tuple("mapperC", SELECT)
                );
    }

    @DisplayName("특정테이블의 특정 컬럼을 사용하는 mapper를 조회할 수 있다.")
    @Test
    void findMappersByCol(){
        // given
        Tables user = Tables.builder().tableName("user").build();
        Tables team = Tables.builder().tableName("team").build();

        Col name = Col.builder().colName("name").build();
        Col password = Col.builder().colName("password").build();
        Col teamname = Col.builder().colName("name").build();
        name.setTable(user);
        password.setTable(user);
        teamname.setTable(team);

        persist(user, team, name, password, teamname);

        Namespace namespace = Namespace.builder().namespace("namespaceA").build();
        Mapper mapperA = Mapper.builder().mapperName("mapperA").elType(SELECT).namespace(namespace).build();
        Mapper mapperB = Mapper.builder().mapperName("mapperB").elType(SELECT).namespace(namespace).build();
        Mapper mapperC = Mapper.builder().mapperName("mapperC").elType(SELECT).namespace(namespace).build();
        persist(namespace, mapperA, mapperB, mapperC,
                MapperCol.builder().col(name).mapper(mapperA).build(),
                MapperCol.builder().col(name).mapper(mapperB).build(),
                MapperCol.builder().col(password).mapper(mapperC).build(),
                MapperCol.builder().col(teamname).mapper(mapperC).build()
        );

        // when
        Page<Mapper> mappersByCol = mapperRepository.findMappersByCol("user", "name", Pageable.ofSize(10));

        // then
        assertThat(mappersByCol).hasSize(2)
                .extracting("mapperName", "elType")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("mapperA", SELECT),
                        Tuple.tuple("mapperB", SELECT)
                );
    }

    @DisplayName("고아 컬럼도 테이블을 미입력시 mapper를 조회할 수 있다.")
    @Test
    void findMappersByCol2(){
        // given
        Tables user = Tables.builder().tableName("user").build();
        Tables team = Tables.builder().tableName("team").build();

        Col name = Col.builder().colName("name").build();
        Col password = Col.builder().colName("password").build();
        Col teamname = Col.builder().colName("name").build();
        name.setTable(user);
        password.setTable(user);
//        teamname.setTable(team);

        persist(user, team, name, password, teamname);

        Namespace namespace = Namespace.builder().namespace("namespaceA").build();
        Mapper mapperA = Mapper.builder().mapperName("mapperA").elType(SELECT).namespace(namespace).build();
        Mapper mapperB = Mapper.builder().mapperName("mapperB").elType(SELECT).namespace(namespace).build();
        Mapper mapperC = Mapper.builder().mapperName("mapperC").elType(SELECT).namespace(namespace).build();
        persist(namespace, mapperA, mapperB, mapperC,
                MapperCol.builder().col(name).mapper(mapperA).build(),
                MapperCol.builder().col(name).mapper(mapperB).build(),
                MapperCol.builder().col(password).mapper(mapperC).build(),
                MapperCol.builder().col(teamname).mapper(mapperC).build()
        );

        // when
        Page<Mapper> mappersByCol = mapperRepository.findMappersByCol("", "name", Pageable.ofSize(10));

        // then
        assertThat(mappersByCol).hasSize(3)
                .extracting("mapperName", "elType")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("mapperA", SELECT),
                        Tuple.tuple("mapperB", SELECT),
                        Tuple.tuple("mapperC", SELECT)
                );
    }

}