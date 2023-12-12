package org.developx.mybatisParser.domain.analysis.sqlparser;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SqlParserResult {

    private final boolean isParsableSql;

    @Builder
    public SqlParserResult(boolean isParsableSql) {
        this.isParsableSql = isParsableSql;
    }

}
