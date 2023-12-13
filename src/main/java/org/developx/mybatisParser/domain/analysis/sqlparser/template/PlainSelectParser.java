package org.developx.mybatisParser.domain.analysis.sqlparser.template;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;

import java.util.List;
import java.util.Map;

@Slf4j
public class PlainSelectParser extends SqlTemplate implements StatementParser<PlainSelect> {

    public PlainSelectParser() {
    }

    public PlainSelectParser(Map tables){
        this.tables = tables;
    }

    @Override
    public ParseResult parse(PlainSelect plainSelect) {

        // from parse
        parseFromItem(plainSelect.getFromItem());

        List<Join> joins = plainSelect.getJoins();
        for (Join join : joins) {
            parseFromItem(join.getFromItem());
        }


        // selectItem parse
        for (SelectItem<?> selectItem : plainSelect.getSelectItems()) {
            parseExpression(selectItem.getExpression());
        }

        return parseResult;
    }
}
