package org.developx.mybatisParser.domain.analysis.sqlparser.template;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;

import java.util.HashMap;
import java.util.Map;

public abstract class SqlTemplate {

    // alias 를 key로 테이블 정보를 저장하고 있습니다.
    protected Map<String, String> tables = new HashMap();

    protected ParseResult parseResult = new ParseResult(true);


    // Deprecated : selectBody
    protected void parseSelect(Select select) {
        if(select instanceof PlainSelect){
            PlainSelect plainSelect = (PlainSelect) select;
            callPlainSelectParse(plainSelect);
        }
    }

    // TODO : visitor pattern 변경해볼까?
    protected void parseFromItem(FromItem fromItem){

        if(fromItem instanceof Table) {
            Table table = (Table) fromItem;
            String alias = table.getAlias() == null ? "" : table.getAlias().getName().toLowerCase();
            tables.put(alias, table.getName());
            parseResult.addTable(table.getName());
        }
    }

    protected void parseExpression(Expression expression) {

    }

    protected void callPlainSelectParse(PlainSelect plainSelect) {
        ParseResult subParseResult = new PlainSelectParser(deepCopyTables()).parse(plainSelect);
        parseResult.mergeTables(subParseResult.getTables());
    }

    private Map<String, String> deepCopyTables(){
        HashMap<String, String> entryMap = new HashMap<>();
        for (Map.Entry<String, String> entry : tables.entrySet()) {
            entryMap.put(entry.getKey(), entry.getValue());
        }
        return entryMap;
    }
}
