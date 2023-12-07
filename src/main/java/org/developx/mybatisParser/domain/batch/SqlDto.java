package org.developx.mybatisParser.domain.batch;

import java.util.List;

public record SqlDto(String text, List<String> conditions) {
}
