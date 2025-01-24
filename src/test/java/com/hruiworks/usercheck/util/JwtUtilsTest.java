package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.pojo.entity.JwtEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class JwtUtilsTest {

    @Test
    public void genAndParse() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("test",1);
        claims.put("test2",2);
        JwtEntity jwtEntity = JwtUtils.generateHs256Jwt(claims, 1, ChronoUnit.WEEKS);
        Claims claims1 = JwtUtils.parseJwt(jwtEntity);

        Assert.assertEquals(1,claims1.get("test"));
        Assert.assertEquals(2,claims1.get("test2"));
    }

}