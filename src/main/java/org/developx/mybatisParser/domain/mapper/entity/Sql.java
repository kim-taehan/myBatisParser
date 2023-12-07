package org.developx.mybatisParser.domain.mapper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Sql {

    @Id
    @GeneratedValue
    @Column(name = "sql_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "mapper_id", nullable = false)
    private Mapper mapper;

    @Lob
    @Column(length = 100000)
    private String originQuery;

    @Builder
    public Sql(Mapper mapper, String originQuery) {
        this.mapper = mapper;
        this.originQuery = originQuery;
    }
}
