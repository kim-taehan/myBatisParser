package org.developx.study.korea;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class koreaTextTest {
    
    @DisplayName("한글 테이블이 있는지 추출한다.")
    @Test
    void test508(){
        String sql = "select a.a1 from 한글테이블 a";
        assertTrue(sql.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"));
    }

    @DisplayName("한글 컬럼 있는지 추출한다.")
    @Test
    void test509(){
        String sql = "select aa.한글Col \n \nfrom a as aa";

        String matcher = "(?s).*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";



        assertTrue(sql.matches(matcher));
    }


    @DisplayName("``로 쌓여진 한글은 무지해야 한다.")
    @Test
    void test510(){

        String ga = "\\'(?:.)*?\\'";
        String sql = ("select aa.bb, '가비지데이터' as test " +
                " 'sdfsdf' as CC" +
                " from a as aa")
                .replaceAll(ga, "''");
        String test = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
        assertFalse(sql.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*"));
    }
}
