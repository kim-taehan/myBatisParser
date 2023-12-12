package org.developx.mybatisParser.domain.analysis.sqlparser.decorator;

public class ParameterDecorator extends Decorator {

    private static final String UPDATE_PARAM_REGEX = "#\\{(?:.|[\\n\\r])*?\\}";

    public ParameterDecorator(Component component) {
        super(component);
    }

    @Override
    public String operation(String text) {
        return text.replaceAll(UPDATE_PARAM_REGEX, "");
    }
}
