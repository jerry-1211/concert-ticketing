package com.jerry.ticketing.global.auth.jwt;


import com.jerry.ticketing.global.auth.oauth.CustomOauth2User;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserService jwtUserService;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        // OAuth2 관련 경로는 JWT 필터를 건너뛰기
        return path.startsWith("/oauth2/") ||
                path.startsWith("/login/oauth2/") ||
                path.startsWith("/member/login/") ||
                path.equals("/") ||
                path.startsWith("/js/") ||
                path.startsWith("/css/") ||
                path.startsWith("/api/webhook/") ||
                "/api/auth/logout".equals(path);

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            MemberDto member = jwtUserService.getMemberByEmail(userEmail);

            CustomOauth2User customOauth2User = new CustomOauth2User(member, Collections.emptyMap(), "sub");
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(customOauth2User, null, customOauth2User.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
