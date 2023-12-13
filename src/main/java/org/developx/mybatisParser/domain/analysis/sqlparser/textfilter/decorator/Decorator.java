package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator;

import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.SqlTextFilter;

public abstract class Decorator implements SqlTextFilter {

    protected final SqlTextFilter sqlTextFilter;
    public Decorator(SqlTextFilter sqlTextFilter) {
        this.sqlTextFilter = sqlTextFilter;
    }

}
