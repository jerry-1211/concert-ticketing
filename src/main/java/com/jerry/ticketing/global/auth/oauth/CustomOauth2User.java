package com.jerry.ticketing.global.auth.oauth;

import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class CustomOauth2User implements OAuth2User {

    @Getter
    private final MemberDto member;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole().name()));
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public String getEmail() {
        return member.getEmail();
    }

    public String getProviderId() {
        return (String) attributes.get(nameAttributeKey);
    }
}
