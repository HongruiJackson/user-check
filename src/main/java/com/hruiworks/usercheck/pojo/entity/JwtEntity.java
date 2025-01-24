package com.hruiworks.usercheck.pojo.entity;

/**
 * @author JacksonZHR
 * 返回生成的jwt及对应的签名key
 */
public class JwtEntity {

    private String jwt;

    private String signKeyStr;

    public JwtEntity(String jwt, String signKeyStr) {
        this.jwt = jwt;
        this.signKeyStr = signKeyStr;
    }

    @Override
    public String toString() {
        return "JwtEntity{" +
                "jwt='" + jwt + '\'' +
                ", signKeyStr='" + signKeyStr + '\'' +
                '}';
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getSignKeyStr() {
        return signKeyStr;
    }

    public void setSignKeyStr(String signKeyStr) {
        this.signKeyStr = signKeyStr;
    }
}
