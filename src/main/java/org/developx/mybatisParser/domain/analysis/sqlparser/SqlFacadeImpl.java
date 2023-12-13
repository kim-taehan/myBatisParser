package org.developx.mybatisParser.domain.analysis.sqlparser;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator.CommentDecorator;
import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.DefaultSqlTextFilter;
import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator.ParameterDecorator;
import org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.SqlTextFilter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqlFacadeImpl implements SqlFacade {

    private final SqlTextFilter filter;

    public SqlFacadeImpl() {
        this.filter = new CommentDecorator(new ParameterDecorator(new DefaultSqlTextFilter()));
    }

    @Override
    public SqlParserResult parser(String sqlText) {

        // TODO : text 길이가 너무 긴 경우 정규식 replaceAll 동작하지 않음..;;
        String changedSql = filter.operation(sqlText);

        try {
            Statement statement = CCJSqlParserUtil.parse(changedSql);
            return SqlParserResult.builder().isParsableSql(true).build();
        } catch (JSQLParserException e) {
            return SqlParserResult.builder().isParsableSql(false).build();
        }
    }
}
