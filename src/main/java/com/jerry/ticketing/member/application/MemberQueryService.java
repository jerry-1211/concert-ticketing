package com.jerry.ticketing.member.application;


import com.jerry.ticketing.global.auth.oauth.userinfo.GoogleUserInfo;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.MemberErrorCode;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    @Transactional
    public <T> T getMemberById(Long memberId, Function<Member, T> mapper) {
        return mapper.apply(memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND)));
    }

    @Transactional
    public <T> T getMemberByEmail(String email, Function<Member, T> mapper) {
        return mapper.apply(
                memberRepository.findByEmail(email)
                        .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND))
        );
    }

    @Transactional
    public MemberDto updateGoogleInfo(GoogleUserInfo googleUserInfo) {
        OffsetDateTime dateTime = OffsetDateTime.now();

        return memberRepository.findByProviderAndProviderId(Provider.GOOGLE, googleUserInfo.getId())
                .map(existingMember -> {
                    existingMember.updateGoogleInfo(googleUserInfo.getName(), googleUserInfo.getPicture(), dateTime);
                    return MemberDto.from(existingMember);
                }).orElseGet(() -> {
                            Member newMember = Member.ofGoogle(googleUserInfo.getName(),
                                    googleUserInfo.getEmail(), googleUserInfo.getId(), googleUserInfo.getPicture(), dateTime);
                            return MemberDto.from(memberRepository.save(newMember));
                        }
                );
    }


}
