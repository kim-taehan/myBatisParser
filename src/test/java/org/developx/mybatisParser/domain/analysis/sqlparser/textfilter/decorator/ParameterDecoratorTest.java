package org.developx.mybatisParser.domain.analysis.sqlparser.textfilter.decorator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[decorator] mybatis parameter 변환 필터")
class ParameterDecoratorTest {

    private ParameterDecorator decorator = new ParameterDecorator(text -> text);

    @Test
    @DisplayName("#{value} -> '' 로 변경함")
    void operation() {
        //given
        String sqlText = "select #{value1} from dual where key = #{value2}";

        String ret = decorator.operation(sqlText);

        assertThat(ret).isEqualTo("select '' from dual where key = ''");
    }
}