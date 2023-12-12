package org.developx.mybatisParser.domain.analysis.sqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.stereotype.Component;

@Component
public class SqlFacadeImpl implements SqlFacade {


    @Override
    public SqlParserResult parser(String sqlText) {

        // TODO : text 길이가 너무 긴 경우 정규식 replaceAll 동작하지 않음..


        try {
            Statement statement = CCJSqlParserUtil.parse(sqlText);
            return SqlParserResult.builder().isParsableSql(true).build();
        } catch (JSQLParserException e) {
            return SqlParserResult.builder().isParsableSql(false).build();
        }
    }
}
