package com.jerry.ticketing.member.infrastructure.repository;

import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.MemberRole;
import com.jerry.ticketing.member.domain.enums.Provider;
import com.jerry.ticketing.member.domain.port.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryAdapterTest {

    @Autowired
    private MemberJpaRepository jpaRepository;

    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepositoryAdapter(jpaRepository);
    }

    @Test
    @DisplayName("멤버를 저장 할 수 있다.")
    void saveMember() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember)
                .extracting("name", "email", "provider",
                        "providerId", "profileImage", "memberRole", "createdAt", "updatedAt")
                .containsExactlyInAnyOrder("jerim", "kj@gmail.com", Provider.GOOGLE,
                        "1234", "Img", MemberRole.USER, dateTime, dateTime
                );
    }

    @Test
    @DisplayName("멤버를 아이디로 찾을 수 있다.")
    void findMemberById() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(memberRepository.findById(savedMember.getId())).isNotNull();
    }

    @Test
    @DisplayName("멤버를 이메일로 찾을 수 있다.")
    void findMemberByEmail() {
        // given
        String email = "kj@gmail.com";
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", email, "1234", "Img", dateTime);

        // when
        memberRepository.save(member);
        Optional<Member> foundMember = memberRepository.findByEmail(email);

        // then
        assertThat(foundMember).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m.getEmail()).isEqualTo(email);
                }
        );
    }

    @Test
    @DisplayName("(구글과 같은) provider,providerId로 멤버를 찾을 수 있다.")
    void findMemberByProviderAndProviderId() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        String providerId = "1234";
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", providerId, "Img", dateTime);

        // when
        memberRepository.save(member);
        Optional<Member> foundMember = memberRepository.findByProviderAndProviderId(Provider.GOOGLE, providerId);

        // then
        assertThat(foundMember).isPresent().hasValueSatisfying(
                m -> {
                    assertThat(m).extracting("provider", "providerId")
                            .containsExactlyInAnyOrder(Provider.GOOGLE, providerId);
                }
        );
    }


}