package com.jerry.ticketing.domain.member;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
    @Column(nullable = false)
    private String password;

    // 멤버 전화번호
    private String phoneNumber;
}
