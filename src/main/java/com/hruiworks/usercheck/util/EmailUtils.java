package com.hruiworks.usercheck.util;

/**
 * 用于处理邮箱相关的校验
 *
 * @author jiushui
 */
public class EmailUtils {

    /**
     * 校验邮箱是否合法
     *
     * @param email 传入的邮箱
     * @return 当且仅当邮箱地址合法返回true
     */
    public static boolean isIllegalEmail(String email) {
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return email.matches(regex);

    }
}
