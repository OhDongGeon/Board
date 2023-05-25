package com.example.board.security;

import com.example.board.domain.type.RankType;
import com.example.board.security.util.Aes256util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;


public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;


    public String createToken(Long userId, String loginId, RankType userRank) {
        Claims claims = Jwts.claims().setId(Aes256util.encrypt(userId.toString()))
            .setSubject(Aes256util.encrypt(loginId));

        claims.put("roles", userRank);

        Date now = new Date();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }


    public Long getTokenUserId(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(Objects.requireNonNull(Aes256util.decrypt(claims.getId())));
    }


    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }


    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
