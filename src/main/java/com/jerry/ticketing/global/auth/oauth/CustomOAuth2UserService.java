package com.jerry.ticketing.global.auth.oauth;


import com.jerry.ticketing.global.auth.oauth.userinfo.GoogleUserInfo;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        GoogleUserInfo googleUserInfo = new GoogleUserInfo(attributes);
        Member member = saveOrUpdate(googleUserInfo);
        return new CustomOauth2User(member, attributes, "sub");
    }


    private Member saveOrUpdate(GoogleUserInfo googleUserInfo) {
        Member member = memberRepository.findByProviderAndProviderId(Provider.GOOGLE, googleUserInfo.getId())
                .map(existingMember -> {
                    existingMember.updateGoogleInfo(googleUserInfo.getName(), googleUserInfo.getPicture());
                    return existingMember;
                }).orElse(Member.ofGoogle(googleUserInfo.getName(), googleUserInfo.getEmail(), googleUserInfo.getId(), googleUserInfo.getPicture()));

        return memberRepository.save(member);
    }
}
