package com.hruiworks.usercheck.exception;

public class UserCheckException extends RuntimeException{

    private String msg;

    public UserCheckException() {

    }

    public UserCheckException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
