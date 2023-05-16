package com.example.board.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;


public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;

    @Value("${spring.jwt.secret}")
    private String secretKey;


    public String createToken(String loginId, String userRank) {
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", userRank);

        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }
}
