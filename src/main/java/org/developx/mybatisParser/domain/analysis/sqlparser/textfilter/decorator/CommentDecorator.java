package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator;

import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.SqlTextFilter;

public class CommentDecorator extends Decorator {

    private static final String REMOVE_COMMENT_REGEX = "/\\*(?:.|[\\n\\r])*?\\*/|(--.*)";

    public CommentDecorator(SqlTextFilter sqlTextFilter) {
        super(sqlTextFilter);
    }

    @Override
    public String operation(String text) {
        return sqlTextFilter.operation(removeComment(text));
    }

    private String removeComment(String text){
        return text.replaceAll(REMOVE_COMMENT_REGEX, "''");
    }
}
