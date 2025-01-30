package com.hruiworks.usercheck.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JacksonZHR
 * 用于处理账号登录名的工具类
 */
public class UsernameUtils {

    /**
     * 判断是否只存在数字、字母、下划线
     */
    public static boolean onlyNumLetterUnderscore(String str) {
        String regex = "^[0-9a-zA-Z_]+$";
        return str.matches(regex);
    }

}
