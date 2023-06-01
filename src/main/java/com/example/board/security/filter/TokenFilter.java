package com.example.board.security.filter;

import com.example.board.domain.repository.UserRepository;
import com.example.board.security.TokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@RequiredArgsConstructor
@WebFilter(urlPatterns = {
    "/rankup/add/*", "/rankup/modify/*", "/rankup/delete/*",
    "/category/add/*", "/category/modify/*", "/category/delete/*",
    "/board/*"
})
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    public final String TOKEN_HEADER = "USER_TOKEN_HEADER";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader(TOKEN_HEADER);
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/board/list") && !StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!StringUtils.hasText(token) || !tokenProvider.validateToken(token)) {
            throw new ServletException("유효하지 않은 접근입니다.");
        }

        userRepository.findByUserId(tokenProvider.getTokenUserId(token))
            .orElseThrow(() -> new ServletException("유효하지 않은 접근입니다."));

        filterChain.doFilter(request, response);
    }
}
