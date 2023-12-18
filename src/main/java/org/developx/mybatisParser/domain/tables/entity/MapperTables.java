package org.developx.mybatisParser.domain.tables.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.domain.mapper.entity.Mapper;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MapperTables {

    @Id
    @GeneratedValue
    @Column(name = "mapper_table_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mapper_id")
    private Mapper mapper;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Tables table;

    @Builder
    public MapperTables(Mapper mapper, Tables table) {
        this.mapper = mapper;
        this.table = table;
    }
}
