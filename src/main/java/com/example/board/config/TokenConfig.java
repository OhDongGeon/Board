package com.example.board.config;

import com.example.board.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TokenConfig {
    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider();
    }
}
