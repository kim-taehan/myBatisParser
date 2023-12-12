package org.developx.mybatisParser.domain.analysis.sqlparser.decorator;

public class DefaultComponent implements Component {
    @Override
    public String operation(String text) {
        return text;
    }
}
