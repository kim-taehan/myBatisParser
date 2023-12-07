package org.developx.mybatisParser.domain.batch;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatchServiceTest {


    @DisplayName("")
    @Test
    void test363(){


        String[] color = {"빨간", "노란", "검정"};
        String[] flute = {"사과", "바나나", "배", "탕수육"};

        String[] strings = sumSql(color, flute);
    }


    private String[] sumSql(String[] origin, String[] addition) {
        int size = origin.length > addition.length ? origin.length : addition.length;
        String[] ret = new String[size];
        for (int i = 0; i < size; i++) {
            ret[i] = origin[i % origin.length] + addition[i % addition.length];
        }
        return ret;
    }

}