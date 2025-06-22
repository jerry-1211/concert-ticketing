package com.jerry.ticketing.member.application.dto.domain;


import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.MemberRole;
import com.jerry.ticketing.member.domain.enums.Provider;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class MemberDto {
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private MemberRole memberRole;
    private Provider provider;
    private String providerId;
    private String profileImage;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .memberRole(member.getMemberRole())
                .provider(member.getProvider())
                .providerId(member.getProviderId())
                .profileImage(member.getProfileImage())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();

    }


}
