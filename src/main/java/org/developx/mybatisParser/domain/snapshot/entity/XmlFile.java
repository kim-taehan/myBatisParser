package org.developx.mybatisParser.domain.snapshot.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.developx.mybatisParser.domain.snapshot.data.enums.AnalysisStatue;
import org.developx.mybatisParser.domain.snapshot.data.enums.ParsingResult;
import org.developx.mybatisParser.global.entity.BaseEntity;

import java.io.File;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static org.developx.mybatisParser.domain.snapshot.data.enums.ParsingResult.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class XmlFile extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "xml_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "snapshot_id", nullable = false)
    private Snapshot snapshot;

    @Column(nullable = false)
    private String absolutePath;

    @Enumerated(STRING)
    private ParsingResult parsingResult;

    @Builder
    public XmlFile(Snapshot snapshot, File file) {
        this.snapshot = snapshot;
        this.absolutePath = file.getAbsolutePath();
    }

    public void success() {
        parsingResult = SUCCESS;
    }
    public void fail() {
        parsingResult = FAIL;
    }
}
