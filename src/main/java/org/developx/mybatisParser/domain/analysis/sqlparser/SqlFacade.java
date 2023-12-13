package org.developx.mybatisParser.domain.analysis.sqlparser;

import org.developx.mybatisParser.domain.analysis.sqlparser.template.data.ParseResult;

public interface SqlFacade {

   ParseResult parser(String sqlText);
}
