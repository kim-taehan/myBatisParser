package org.developx.mybatisParser.domain.analysis.sqlparser.template;

import net.sf.jsqlparser.statement.Statement;
import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;

public interface StatementParser<T extends Statement> {
    ParseResult parse(T t);
}
