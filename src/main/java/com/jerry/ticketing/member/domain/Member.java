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

    // 멤버 주소
    @Embedded
    private Address address;

    // 멤버 이름
    @Column(nullable = false)
    private String name;

    // 멤버 이메일
    @Column(nullable = false)
    private String email;

    // 멤버 패스워드
    @Column
    private String password;

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


    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public static Member ofGoogle(String name, String email, String providerId, String profileImage) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.provider = Provider.GOOGLE;
        member.providerId = providerId;
        member.profileImage = profileImage;
        member.memberRole = MemberRole.USER;
        return member;
    }


    public void updateGoogleInfo(String name, String profileImage) {
        this.name = name;
        this.profileImage = profileImage;
    }

    public void updateProfile(UpdateProfile.Request request) {
        if (request.getName() != null) {
            this.name = request.getName();
        }

        if (request.getAddress() != null) {
            this.address = request.getAddress();
        }

        if (request.getPhoneNumber() != null) {
            this.phoneNumber = request.getPhoneNumber();
        }
    }

}
