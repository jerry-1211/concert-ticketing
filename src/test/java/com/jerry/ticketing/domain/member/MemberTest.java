package com.jerry.ticketing.domain.member;

import com.jerry.ticketing.repository.member.MemberRepository;
import com.jerry.ticketing.domain.TestFixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 생성 및 저장 검증")
    void saveMember(){
        // Given
        Member member = TestFixture.createMember();

        // When
        Member saveMember = memberRepository.save(member);

        // Then
        assertThat(saveMember).isNotNull();
        assertThat(saveMember.getId()).isNotNull();
        assertThat(saveMember.getAddress().getCity()).isEqualTo("경기도");
        assertThat(saveMember.getEmail()).isEqualTo("jerry@naver.com");
        assertThat(saveMember.getPhoneNumber()).isEqualTo("010-2304-4302");
    }

}