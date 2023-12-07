package org.developx.mybatisParser.global.properties.items;

import org.developx.mybatisParser.SpringBootTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

@DisplayName("[properties] file check 프로퍼티")
class FileCheckPropertiesTest extends SpringBootTestSupport {

    @Autowired
    private FileCheckProperties fileCheckProperties;

    // start_with: sql, query
    // ends_with: .xml
    @DisplayName("mapper xml 파일 이름에서 확장자나 prefix 등과 비교하여 mapper 대상건을 추출한다.")
    @CsvSource({"sqlTest.xml, true", "queryTest.xml, true", "mapperTest.xml, false", "queryTest.txt, false", "sqlTest.java, false"})
    @ParameterizedTest
    void checkFileName(String filename, boolean expected){
        // given
        // when
        boolean ret = fileCheckProperties.checkedFileName(filename);

        // then
        assertThat(ret).isEqualTo(expected);
    }

    // /class/, /test/
    @DisplayName("mapper xml 찾는 경로로 특정 구역이 있는 경우 대상에서 제외한다.")
    @CsvSource({"/user/class/file.xml, true"
            , "/user/class2/file.xml, false"
            , "/user/normal/test.xml, false"
            , "/user/test/test.xml, true"
            , "/user/normal-class/class.xml, false"
    })
    @ParameterizedTest
    void uncheckedDirPath(String path, boolean expected){
        // given
        // when
        boolean ret = fileCheckProperties.uncheckedDirPath(Path.of(path));

        // then
        assertThat(ret).isEqualTo(expected);
    }




}