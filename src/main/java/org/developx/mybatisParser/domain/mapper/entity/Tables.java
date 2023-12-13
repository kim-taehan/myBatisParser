package org.developx.mybatisParser.domain.mapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Tables {

    @Id
    @GeneratedValue
    @Column(name = "table_id")
    private Long id;

    @Column(length = 50)
    private String tableName;




}
