package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter;

public class DefaultSqlTextFilter implements SqlTextFilter {
    @Override
    public String operation(String text) {
        return text;
    }
}
