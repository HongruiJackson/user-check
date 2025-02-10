package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.enums.MessageDigestAlgorithmEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author JacksonZHR
 * 密码相关工具类
 */
public class PasswordUtils {

    /**
     * 密码加密
     *
     * @param str           要加密的字符串
     * @param algorithmEnum 信息摘要算法
     * @return 加密后字符串
     */
    public static String encryptPassword(String str, MessageDigestAlgorithmEnum algorithmEnum) {
        return switch (algorithmEnum) {
            case MD2 -> DigestUtils.md2Hex(str);
            case MD5 -> DigestUtils.md5Hex(str);
            case SHA1 -> DigestUtils.sha1Hex(str);
            case SHA256 -> DigestUtils.sha256Hex(str);
            case SHA3_224 -> DigestUtils.sha3_224Hex(str);
            case SHA3_256 -> DigestUtils.sha3_256Hex(str);
            case SHA3_384 -> DigestUtils.sha3_384Hex(str);
            case SHA3_512 -> DigestUtils.sha3_512Hex(str);
            case SHA384 -> DigestUtils.sha384Hex(str);
            case SHA512_224 -> DigestUtils.sha512_224Hex(str);
            case SHA512_256 -> DigestUtils.sha512_256Hex(str);
            case SHA512 -> DigestUtils.sha512Hex(str);
        };

    }

    /**
     * 密码加密
     *
     * @param str           要加密的字符串
     * @param salt          “盐”
     * @param algorithmEnum 信息摘要算法
     * @return 加密后字符串
     */
    public static String encryptPassword(String str, String salt, MessageDigestAlgorithmEnum algorithmEnum) {
        return encryptPassword(salt + str, algorithmEnum);
    }

    /**
     * 比较密码
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 当且仅当可比较的字符串相同的时候返回真
     */
    public static boolean comparePassword(String str1, String str2) {
        if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
            return false;
        }
        return StringUtils.compare(str1, str2) == 0;
    }
}
