package com.hruiworks.usercheck.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    public static String generateJWT(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "duwjdiojnxc")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 172800000L))
                .compact();
    }
}
