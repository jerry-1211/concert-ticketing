package com.jerry.ticketing.member.application.dto.domain;


import com.jerry.ticketing.member.domain.Address;
import com.jerry.ticketing.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {
    private Long memberId;
    private String name;
    private String email;
    private String phoneNumber;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();


    }

}
