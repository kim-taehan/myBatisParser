package org.developx.mybatisParser.domain.analysis.sqlparser.decorator;

public abstract class Decorator implements Component {

    protected final Component component;
    public Decorator(Component component) {
        this.component = component;
    }

}
