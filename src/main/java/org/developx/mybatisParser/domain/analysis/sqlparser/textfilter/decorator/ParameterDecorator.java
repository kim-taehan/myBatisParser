package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator;

import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.SqlTextFilter;

public class ParameterDecorator extends Decorator {

    private static final String UPDATE_PARAM_REGEX = "#\\{(?:.|[\\n\\r])*?\\}";

    public ParameterDecorator(SqlTextFilter sqlTextFilter) {
        super(sqlTextFilter);
    }

    @Override
    public String operation(String text) {
        return sqlTextFilter.operation(converterParam(text));
    }

    private String converterParam(String text){
        return text.replaceAll(UPDATE_PARAM_REGEX, "''");
    }
}
