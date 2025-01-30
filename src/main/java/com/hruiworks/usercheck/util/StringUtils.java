package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.enums.StringUtilsExceptionEnum;
import com.hruiworks.usercheck.exception.UserCheckException;

import java.util.Objects;

/**
 * @author JacksonZHR
 * 部分字符串处理的工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否在某个闭区间
     * @param str 字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 当且仅当该字符串在该区间返回true
     */
    public static boolean strLengthBetween(String str, int minLength, int maxLength) {
        if (Objects.isNull(str)) {
            return false;
        }
        if (minLength < 0 || maxLength < minLength) {
            throw new UserCheckException(StringUtilsExceptionEnum.WRONG_RANGE.getMsg());
        }
        return (str.length() >= minLength && str.length() <= maxLength);
    }

    /**
     * 大于某个值
     * @param str 字符串
     * @param minLength 大于的值
     * @return 满足条件返回true
     */
    public static boolean strLengthGT(String str, int minLength) {
        return strLengthBetween(str,minLength+1,Integer.MAX_VALUE);
    }

    /**
     * 大于等于某个值
     * @param str 字符串
     * @param minLength 大于等于的值
     * @return 满足条件返回true
     */
    public static boolean strLengthGTE(String str, int minLength) {
        return strLengthBetween(str,minLength,Integer.MAX_VALUE);
    }

    /**
     * 小于某个值
     * @param str 字符串
     * @param maxLength  小于的值
     * @return 满足条件返回true
     */
    public static boolean strLengthLT(String str, int maxLength) {
        return strLengthBetween(str,0,maxLength-1);
    }

    /**
     * 小于等于某个值
     * @param str 字符串
     * @param maxLength  小于等于的值
     * @return 满足条件返回true
     */
    public static boolean strLengthLTE(String str, int maxLength) {
        return strLengthBetween(str,0,maxLength);
    }


}
