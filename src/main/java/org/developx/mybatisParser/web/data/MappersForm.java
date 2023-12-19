package org.developx.mybatisParser.web.data;

public record MappersForm (
        String fullName,
        String tableName,
        String colName
){
    public MappersForm(String fullName, String tableName, String colName) {
        this.fullName = fullName;
        this.tableName = tableName;
        this.colName = colName;
    }

    public static MappersForm init(){
        return new MappersForm("", "", "");
    }
}