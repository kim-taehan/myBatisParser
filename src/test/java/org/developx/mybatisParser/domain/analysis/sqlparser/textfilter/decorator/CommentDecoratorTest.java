package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[decorator] SQL 주석 제거 필터")
class CommentDecoratorTest {

    // DefaultDecorator가 변경될 수 있기에 단순 람다 형태로 구현
    private CommentDecorator decorator = new CommentDecorator(text -> text);

    @DisplayName("'--' 으로 시작되는 주석을 제거할 수 있다.")
    @Test
    void removeLineComment(){

        // given
        String sqlText = "select d.* from dual d -- comment";

        // when
        String ret = decorator.operation(sqlText);

        // then
        assertThat(ret).isEqualTo("select d.* from dual d ");
    }

    /* */
    @DisplayName("'/* */' 으로 되어 있는 주석을 제거할 수 있다.")
    @Test
    void removeContextComment(){

        // given
        String sqlText = "select d.* from /*\n" +
                " context comment \n" +
                "*/dual d ";

        // when
        String ret = decorator.operation(sqlText);

        // then
        assertThat(ret).isEqualTo("select d.* from dual d ");
    }


}