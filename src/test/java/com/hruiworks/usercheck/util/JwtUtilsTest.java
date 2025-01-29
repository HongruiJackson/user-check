package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.pojo.entity.JwtEntity;
import com.hruiworks.usercheck.testEntity.User;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;

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

    @Test
    public void parseReflectTest() {

        Map<String, Object> claims = new HashMap<>();
        claims.put("nameOne","jack");
        claims.put("age",2);

        Map<String, Object> claims1 = new HashMap<>();
        claims1.put("nameOne","jacky");
        claims1.put("age",21);

        JwtEntity jwtEntity = JwtUtils.generateHs256Jwt(claims, 1, ChronoUnit.WEEKS);
        JwtEntity jwtEntity1 = JwtUtils.generateHs256Jwt(claims1, 1, ChronoUnit.WEEKS);
        Class<User> userClass = User.class;
        User user = JwtUtils.parseJwt(jwtEntity, userClass);
        User user1 = JwtUtils.parseJwt(jwtEntity1, userClass);
        System.out.println(user);
        System.out.println(user1);

    }

    @Test
    public void generateReflectTest() {
        User user = new User();
        user.setAge(2);
//        user.setNameOne("jack");
        JwtEntity jwtEntity = JwtUtils.generateHs256Jwt(user, 1, ChronoUnit.WEEKS);
        User user1 = JwtUtils.parseJwt(jwtEntity, User.class);
        System.out.println(user1);
    }
}