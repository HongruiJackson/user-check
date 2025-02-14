package com.hruiworks.usercheck.util;

/**
 * 电话号码校验工具类
 *
 * @author jiushui
 */
public class PhoneNumberUtils {

    /**
     * 校验是不是中国的手机号码
     *
     * @param phoneNumber 手机号码
     * @return 当且仅当符合要求是返回true
     */
    public static boolean isIllegalChinaPhoneNumber(String phoneNumber) {
        String regex = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        return phoneNumber.matches(regex);
    }
}
