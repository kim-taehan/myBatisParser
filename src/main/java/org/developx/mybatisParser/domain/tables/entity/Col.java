package org.developx.mybatisParser.domain.tables.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Col {

    @Id
    @GeneratedValue
    @Column(name = "col_id")
    private Long id;

    @Column(length = 50)
    private String colName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = true) // 고아 column 존재할 수 있다.
    private Tables table;

    @Builder
    public Col(String colName) {
        this.colName = colName;
    }

    public void setTable(Tables table) {
        this.table = table;
        table.addCol(this);
    }
}
