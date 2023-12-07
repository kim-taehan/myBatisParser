package org.developx.mybatisParser.global.properties.items;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;
import java.util.List;

@ConfigurationProperties(prefix = "file-check")
@RequiredArgsConstructor
public class FileCheckProperties {

    private final List<String> startWith;
    private final List<String> endsWith;
    private final List<String> uncheckPaths;

    public boolean uncheckedDirPath(Path path) {
        String absolutePath = path.toFile().getAbsolutePath();
        return uncheckPaths.stream()
                .filter(absolutePath::contains)
                .findFirst()
                .isPresent();
    }

    public boolean checkedFileName(String fileName) {
        return checkedStartWith(fileName) && checkedEndWith(fileName);
    }

    private boolean checkedStartWith(String fileName) {
        for (String text : startWith) {
            if (fileName.startsWith(text)) {
                return true;
            }
        }
        return startWith.size() == 0 ? true : false;
    }

    private boolean checkedEndWith(String fileName) {
        for (String text : endsWith) {
            if (fileName.endsWith(text)) {
                return true;
            }
        }
        return endsWith.size() == 0 ? true : false;
    }

}
