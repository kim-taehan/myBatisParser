package org.developx.mybatisParser.domain.batch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Getter
public class MapperParseResult {

    private final Map<String, Set<String>> tables = new HashMap<>();

    public void addTable(String tableName) {
        if(!tables.containsKey(tableName)) {
            tables.put(tableName, new HashSet<>());
        }
    }

    public void addColumn(String tableName, String columnName){

        Set<String> findCols = tables.getOrDefault(tableName, new HashSet<>());
        findCols.add(columnName);

        tables.put(tableName, findCols);
    }

    private void addAllColumn(String tableName, Set<String> columns){
        addTable(tableName);
        columns.stream().forEach(column -> addColumn(tableName, column));
    }


    public void mergeTables(Map<String, Set<String>> tables) {
        for (String tableName : tables.keySet()) {
            addAllColumn(tableName, tables.get(tableName));
        }
    }
}
