package org.developx.mybatisParser.domain.snapshot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.domain.snapshot.data.enums.AnalysisStatue;
import org.developx.mybatisParser.global.entity.BaseEntity;

import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;
import static org.developx.mybatisParser.domain.snapshot.data.enums.AnalysisStatue.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Snapshot extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "snapshot_id")
    private Long id;

    private int xmlFileSize;

    @Enumerated(STRING)
    private AnalysisStatue analysisStatue;


    @Builder
    public Snapshot(int xmlFileSize) {
        this.xmlFileSize = xmlFileSize;
        this.analysisStatue = READY;
    }
}
