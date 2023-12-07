package org.developx.mybatisParser.domain.mapper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.domain.mapper.data.ElType;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Mapper {

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

    @Builder
    public Mapper(Namespace namespace, ElType elType, String mapperName, String xml) {
        this.namespace = namespace;
        this.elType = elType;
        this.mapperName = mapperName;
        this.fullName = String.format("%s.%s", namespace.getNamespace(), mapperName);
        this.xml = xml;
    }
}
