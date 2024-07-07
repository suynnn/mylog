package org.mylog.global.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; // 30분 = 30 * 60 * 1000L
    public static Long REFRESH_TOKEN_EXPIRE_COUNT = 7 * 24 * 60 * 60 * 1000L; // 7일 = 7 * 24 * 60 * 60 * 1000L

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret,
                        @Value("${jwt.refreshKey}") String refreshSecret) {

        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    public static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }

    // ACCESS Token 생성
    public String createAccessToken(Long id, String email, String name, String username,
                                    List<String> roles, Long blogId) {

        Claims claims = Jwts.claims().setSubject(email);

        claims.put("userId", id);
        claims.put("username", username);
        claims.put("name", name);
        claims.put("roles", roles);
        claims.put("blogId", blogId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+ACCESS_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(accessSecret))
                .compact();
    }

    // Refresh Token 생성
    public String createRefreshToken(Long id, String email) {

        Claims claims = Jwts.claims().setSubject(email);

        claims.put("userId", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+REFRESH_TOKEN_EXPIRE_COUNT))
                .signWith(getSigningKey(refreshSecret))
                .compact();
    }

    public Long getUserIdFromToken(String token){
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer)claims.get("userId"));
    }

    public Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    public boolean isTokenExpired(String token, byte[] secretKey) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isRefreshTokenExpired(String token) {
        return isTokenExpired(token, refreshSecret);
    }
}
