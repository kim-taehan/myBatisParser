package org.developx.mybatisParser.domain.analysis.sqlparser.decorator;

public class CommentDecorator extends Decorator {

    private static final String REMOVE_COMMENT_REGEX = "/\\*(?:.|[\\n\\r])*?\\*/|(--.*)";

    public CommentDecorator(Component component) {
        super(component);
    }

    @Override
    public String operation(String text) {
        return text.replaceAll(REMOVE_COMMENT_REGEX, "");
    }
}
