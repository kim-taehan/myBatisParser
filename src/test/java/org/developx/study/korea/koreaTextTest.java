package org.developx.study.korea;

import groovy.util.logging.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class koreaTextTest {


    //User
    // ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"
    private String MATCHER =  "(?i)^(?:(?!\\bas\\s)[\\s\\S])*?[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    private String REMOVE_TEXT = "\\'(?:.)*?\\'";


    private  Matcher getMatcher(String sql) {
        sql = sql.replaceAll(REMOVE_TEXT, "''");
        Matcher matcher = Pattern.compile(MATCHER).matcher(sql);

        boolean ret = matcher.find();

        if(ret){
            String substring = sql.substring(matcher.start(), matcher.end());
            String[] split = substring.split("\n");
            System.out.println(split[split.length-1]);
        }

        return Pattern.compile(MATCHER, Pattern.COMMENTS).matcher(sql);
    }

    @DisplayName("한글 테이블이 있는지 추출한다.")
    @Test
    void checkKoreanTableName(){
        // given
        String sql = "\n select * \nfrom 한글테이블 ";

        // when
        Matcher matcher = getMatcher(sql);

        // then
        Assertions.assertTrue(matcher.find());
    }

    @DisplayName("한글 컬럼이 있는지 추출한다.")
    @Test
    void checkKoreanColumnName(){
        // given
        String sql = "\n select tb.한글컬럼 \nfrom aa \nas 얼리어스";

        // when
        Matcher matcher = getMatcher(sql);

        // then
        Assertions.assertTrue(matcher.find());
    }

    @DisplayName("text로 존재하는 한글은 대상에서 제외한다..")
    @Test
    void checkKoreanText(){
        // given
        String sql = "\n select tb.aa, '한글텍스트' as bb \nfrom aa \nas aa";

        // when
        Matcher matcher = getMatcher(sql);

        // then
        Assertions.assertFalse(matcher.find());
    }


    @DisplayName("as 정규식은 제외되도록 하자")
    @Test
    void test903(){
        String sql = "\n select tb.aa \nfrom aa \nas 한글";

        // when
        Matcher matcher = getMatcher(sql);

        // then
        Assertions.assertFalse(matcher.find());
    }


    @DisplayName("")
    @Test
    void test585(){
        String sql = "\n select tb.as \nfrom 한글테이블";

        // when
        Matcher matcher = getMatcher(sql);

        // then
        Assertions.assertFalse(matcher.find());
    }



}
