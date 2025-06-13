package com.jerry.ticketing.member.domain;


import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false)
    private String password;

    // 멤버 전화번호
    private String phoneNumber;


    private Member(String name, Address address, String email, String password, String phoneNumber) {
        this.address = address;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static Member createMember(String name, Address address,
                                      String email, String password, String phoneNumber) {
        return new Member(name, address, email, password, phoneNumber);
    }
}
