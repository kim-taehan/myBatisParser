package org.developx.mybatisParser.domain.analysis.sqlparser.template;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Select;
import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;

@Slf4j
public class SelectParser extends SqlTemplate implements StatementParser<Select> {

    @Override
    public ParseResult parse(Select select) {
        parseSelect(select);
        return parseResult;
    }
}
