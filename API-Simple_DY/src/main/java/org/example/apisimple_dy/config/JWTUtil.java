package org.example.apisimple_dy.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class JWTUtil {

    private static final String secretKey = "API_SIMPLE_DY"; // 请使用更安全的密钥

    // 生成Token的详细方法
    public String doGenerateToken(Map<String, Object> claims, Integer userID) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userID.toString()) // 用用户ID作为主标识符
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7天过期时间
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // 验证Token
    public Boolean validateToken(String token, String userName) {
        final String tokenUserName = getUsernameFromToken(token);
        return (tokenUserName.equals(userName) && !isTokenExpired(token));
    }

    public static Claims parseToken(String token) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public static Integer getUserIdFromToken(String token) throws SignatureException {
        Claims claims = parseToken(token);
        return Integer.parseInt(claims.getSubject());
    }
    // 从Token中获取用户名
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // 从Token中获取过期时间
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // 从Token中获取任意信息
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 从Token中获取所有信息
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查Token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}

