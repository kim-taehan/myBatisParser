package org.developx.mybatisParser.domain.tables.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Col> cols = new ArrayList<>();

    @OneToMany(mappedBy = "table")
    private List<MapperTables> mapperTables = new ArrayList<>();

    @Builder
    public Tables(String tableName) {
        this.tableName = tableName;
    }

    public void addCol(Col col){
        cols.add(col);
    }
}
