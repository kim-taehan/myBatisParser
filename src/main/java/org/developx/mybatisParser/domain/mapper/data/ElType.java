package org.developx.mybatisParser.domain.mapper.data;

public enum ElType {
    NONE
    ,CACHE
    ,CACHE_REF
    ,RESULTMAP
    ,PARAMETERMAP
    ,SQL
    ,INSERT
    ,UPDATE
    ,DELETE
    ,SELECT;


    public static ElType valueOfCustom(String name) {
        try {
            return ElType.valueOf(name.toUpperCase().replace("_", "-"));
        } catch (RuntimeException e) {
            return ElType.NONE;
        }
    }




}
