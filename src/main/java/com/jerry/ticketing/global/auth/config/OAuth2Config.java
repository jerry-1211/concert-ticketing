package com.jerry.ticketing.global.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google.oauth2")
public class OAuth2Config {
    private String authorizedRedirectUri;
    
}
