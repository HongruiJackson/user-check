package com.hruiworks.usercheck.util;

import com.hruiworks.usercheck.pojo.entity.JwtEntity;
import com.hruiworks.usercheck.pojo.reflect.ReflectEntity;
import com.hruiworks.usercheck.support.ReflectCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.*;

/**
 * @author JacksonZHR
 * 用于生成和解析jwt的工具类
 */
public class JwtUtils {

    /**
     * 生成hs256的jwt和对应的签名key
     * @param claims jwt payload map
     * @param time 过期时间
     * @param unit 过期时间单位
     * @return 生成的jwt及对应的签名key
     */
    public static JwtEntity generateHs256Jwt(Map<String, Object> claims, long time, TemporalUnit unit) {

        long millis = 0L;
        if (unit instanceof ChronoUnit chronoUnit) {
            millis = switch (chronoUnit) {
                case NANOS -> time / MILLIS.getDuration().toNanos();
                case MICROS -> time / 1000;
                case MILLIS -> time;
                case SECONDS -> time * SECONDS.getDuration().toMillis();
                case MINUTES -> time * MINUTES.getDuration().toMillis();
                case HOURS -> time * HOURS.getDuration().toMillis();
                case HALF_DAYS -> time * DAYS.getDuration().toMillis() / 2;
                case DAYS -> time * DAYS.getDuration().toMillis();
                case WEEKS -> time * WEEKS.getDuration().toMillis();
                case MONTHS -> time * MONTHS.getDuration().toMillis();
                case YEARS -> time * YEARS.getDuration().toMillis();
                case DECADES -> time * DECADES.getDuration().toMillis();
                case CENTURIES -> time * CENTURIES.getDuration().toMillis();
                case MILLENNIA -> time * MILLENNIA.getDuration().toMillis();
                case ERAS -> time * ERAS.getDuration().toMillis();
                case FOREVER -> time * FOREVER.getDuration().toMillis();
                default -> millis;
            };
        }

        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        String signKeyStr = Encoders.BASE64.encode(secretKey.getEncoded());

        String jwt = Jwts.builder()
                .signWith(secretKey)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + millis))
                .compact();

        return new JwtEntity(jwt,signKeyStr);
    }

    /**
     * 生成jwt，用已有的对象去生成
     * @param sourceObject 要封装的对象
     * @param time 过期时间
     * @param unit 过期时间单位
     * @return 生成的jwt及对应的签名key
     */
    public static <T> JwtEntity generateHs256Jwt( T sourceObject, long time, TemporalUnit unit) {
        Map<String, Object> claims = new HashMap<>();
        Class<T> aClass = (Class<T>) sourceObject.getClass();
        ReflectEntity<T> tReflectEntity = ReflectCache.get(aClass);
        Map<String, Method> fieldGetter = tReflectEntity.getFieldGetter();
        Set<String> fieldNameSet = fieldGetter.keySet();

        try {
            for (String fieldName : fieldNameSet) {
                Method getter = fieldGetter.get(fieldName);
                Object invoke = getter.invoke(sourceObject);
                if (Objects.isNull(invoke)) {
                    continue;
                }
                claims.put(fieldName,invoke);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return generateHs256Jwt(claims,time,unit);

    }


    /**
     * 解析jwt
     * @param jwtEntity 生成的jwt和对应的签名key
     * @return payload的claims
     */
    public static Claims parseJwt(JwtEntity jwtEntity) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtEntity.getSignKeyStr())))
                .build()
                .parseSignedClaims(jwtEntity.getJwt())
                .getPayload();

    }

    /**
     * 解析jwt，并将其转换为对应的目标对象
     * @param jwtEntity 生成的jwt和对应的签名key
     * @param targetClass 目标类
     * @return 目标对象
     */
    public static <T> T parseJwt(JwtEntity jwtEntity, Class<T> targetClass) {

        ReflectEntity<T> reflectEntity = ReflectCache.get(targetClass);
        T targetObject;
        try {
            targetObject = reflectEntity.getNonArgConstructor().newInstance();
            Claims payload = parseJwt(jwtEntity);
            Set<String> payloadKeySet = payload.keySet();
            Map<String, Method> fieldSetter = reflectEntity.getFieldSetter();
            for (String payloadKey : payloadKeySet) {
                if (Objects.isNull(fieldSetter.get(payloadKey))) {
                    continue;
                }
                fieldSetter.get(payloadKey).invoke(targetObject,payload.get(payloadKey));
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return targetObject;

    }
}
