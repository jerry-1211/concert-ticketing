package com.jerry.ticketing.global.auth.oauth;


import com.jerry.ticketing.global.auth.config.OAuth2Config;
import com.jerry.ticketing.global.auth.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2Config oAuth2Config;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {


        CustomOauth2User principal = (CustomOauth2User) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(principal);

        String targetUrl = UriComponentsBuilder.fromUriString(oAuth2Config.getAuthorizedRedirectUri())
                .queryParam("token", token)
                .queryParam("type", "google")
                .build().toUriString();


        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
