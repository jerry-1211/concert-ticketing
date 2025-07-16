package com.jerry.ticketing.member.domain;


import com.jerry.ticketing.member.application.dto.UpdateProfile;
import com.jerry.ticketing.member.domain.enums.MemberRole;
import com.jerry.ticketing.member.domain.enums.Provider;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    // 멤버 id
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 멤버 이름
    @Column(nullable = false)
    private String name;

    // 멤버 이메일
    @Column(nullable = false)
    private String email;

    // 멤버 전화번호
    @Column
    private String phoneNumber;

    // 멤버 역할
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    // 로그인 방식
    @Enumerated(EnumType.STRING)
    private Provider provider;

    // 구글 제공 고유 Id
    @Column
    private String providerId;

    // 프로필
    @Column
    private String profileImage;

    // 회원가입 시간
    private OffsetDateTime createdAt;

    // 회원수정 시간
    private OffsetDateTime updatedAt;


    public static Member ofGoogle(String name, String email, String providerId, String profileImage, OffsetDateTime dateTime) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.provider = Provider.GOOGLE;
        member.providerId = providerId;
        member.profileImage = profileImage;
        member.memberRole = MemberRole.USER;
        member.onCreate(dateTime);
        return member;
    }

    public void updateGoogleInfo(String name, String profileImage, OffsetDateTime dateTime) {
        this.name = name;
        this.profileImage = profileImage;
        onUpdate(dateTime);
    }

    public void updateProfile(UpdateProfile.Request request) {
        updateName(request.getName());
        updatePhoneNumber(request.getPhoneNumber());
    }

    private void onCreate(OffsetDateTime dateTime) {
        createdAt = dateTime;
        updatedAt = dateTime;
    }

    private void onUpdate(OffsetDateTime dateTime) {
        updatedAt = dateTime;
    }

    private void updatePhoneNumber(String PhoneNumber) {
        if (PhoneNumber != null) {
            this.phoneNumber = PhoneNumber;
        }
    }

    private void updateName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

}
