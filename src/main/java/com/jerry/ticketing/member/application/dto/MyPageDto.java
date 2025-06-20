package com.jerry.ticketing.member.application.dto;


import com.jerry.ticketing.member.domain.Address;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class MyPageDto {

    private String name;
    private String email;
    private String phoneNumber;
    private Provider provider;
    private String profileImage;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static MyPageDto from(Member member) {
        return MyPageDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .provider(member.getProvider())
                .profileImage(member.getProfileImage())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
