package com.hruiworks.usercheck.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.*;

/**
 * @author JacksonZHR
 */
public class JwtUtils {

    public static String generateHs256Jwt(Map<String, Object> claims, long time, TemporalUnit unit) {

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

        return Jwts.builder()
                .signWith(Jwts.SIG.HS256.key().build())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + millis))
                .compact();
    }
}
