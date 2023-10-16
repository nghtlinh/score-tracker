package com.example.scoretracker.security;
import com.example.scoretracker.service.login.CustomUserDetails;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String JWT_SECRET = "secret_happy";
    private static final long JWT_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
    private static final String USERNAME_KEY = "username";

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + JWT_EXPIRE_TIME);
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_KEY, userDetails.getUsername());
        claims.put("permission", userDetails.getAuthorities());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJwt(String token) {
        //decode the token
        Claims body = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();
        return (String) body.get(USERNAME_KEY);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> p = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}

