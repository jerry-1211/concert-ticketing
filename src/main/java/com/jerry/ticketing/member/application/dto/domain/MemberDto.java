package com.jerry.ticketing.member.application.dto.domain;


import com.jerry.ticketing.member.domain.Address;
import com.jerry.ticketing.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private Address address;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getAddress(),
                member.getName(),
                member.getEmail(),
                member.getPassword(),
                member.getPhoneNumber()
        );
    }


}
