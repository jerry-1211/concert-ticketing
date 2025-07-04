package com.jerry.ticketing.global.auth.config;


import com.jerry.ticketing.global.auth.jwt.JwtAuthenticationFilter;
import com.jerry.ticketing.global.auth.oauth.CustomOAuth2UserService;
import com.jerry.ticketing.global.auth.oauth.OAuth2AuthenticationFailureHandler;
import com.jerry.ticketing.global.auth.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!test")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler auth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler auth2AuthenticationFailureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/js/**", "/css/**").permitAll()
                        .requestMatchers("/api/webhook/**", "/api/auth/logout").permitAll()
                        .requestMatchers("/oauth2/**", "/login/oauth2/code/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(auth2AuthenticationSuccessHandler)
                        .failureHandler(auth2AuthenticationFailureHandler))
                .rememberMe(AbstractHttpConfigurer::disable);


        return http.build();
    }

}
