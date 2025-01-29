package com.hruiworks.usercheck.exception;

public class ReflectCacheException extends RuntimeException{

    private String msg;

    public ReflectCacheException() {

    }

    public ReflectCacheException(String msg) {
        this.msg = msg;
    }

}
