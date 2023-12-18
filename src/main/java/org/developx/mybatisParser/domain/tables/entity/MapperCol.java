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
public class MapperCol {

    @Id
    @GeneratedValue
    @Column(name = "mapper_table_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mapper_id")
    private Mapper mapper;

    @ManyToOne
    @JoinColumn(name = "col_id")
    private Col col;

    @Builder
    public MapperCol(Mapper mapper, Col col) {
        this.mapper = mapper;
        this.col = col;
    }
}
