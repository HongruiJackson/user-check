package com.hruiworks.usercheck.enums;

public enum ReflectCacheExceptionEnum {

    WRONG_TYPE("类型转换错误"),

    NO_NON_ARG_CONSTRUCTOR("没有无参构造器"),

    ;
    private final String msg;

    ReflectCacheExceptionEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
