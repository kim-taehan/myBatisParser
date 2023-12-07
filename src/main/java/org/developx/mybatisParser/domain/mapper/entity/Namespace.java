package org.developx.mybatisParser.domain.mapper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.global.entity.BaseEntity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Namespace extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "mapper_id")
    private Long id;

    @Column(updatable = false, unique = true)
    private String namespace;

    @Builder
    public Namespace(String namespace) {
        this.namespace = namespace;
    }
}
