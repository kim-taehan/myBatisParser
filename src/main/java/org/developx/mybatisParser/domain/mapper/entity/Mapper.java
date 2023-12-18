package org.developx.mybatisParser.domain.mapper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.domain.mapper.data.ElType;
import org.developx.mybatisParser.domain.tables.entity.MapperTables;
import org.developx.mybatisParser.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mapper extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mapper_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "namespace_id", nullable = false)
    private Namespace namespace;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ElType elType;

    private String mapperName;

    private String fullName;

    @Lob
    @Column(length = 100000)
    private String xml;

    @OneToMany(mappedBy = "mapper")
    private List<MapperTables> mapperTables = new ArrayList<>();

    @Builder
    public Mapper(Namespace namespace, ElType elType, String mapperName, String xml) {
        this.namespace = namespace;
        this.elType = elType;
        this.mapperName = mapperName;
        this.fullName = String.format("%s.%s", namespace.getNamespace(), mapperName);
        this.xml = xml;
    }
}
