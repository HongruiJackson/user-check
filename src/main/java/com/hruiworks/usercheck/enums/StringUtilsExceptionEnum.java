package com.hruiworks.usercheck.enums;

public enum StringUtilsExceptionEnum {

    WRONG_RANGE("不合法的范围"),

    ;
    private final String msg;

    StringUtilsExceptionEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
