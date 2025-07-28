package com.jerry.ticketing.member.application;

import com.jerry.ticketing.global.auth.oauth.userinfo.GoogleUserInfo;
import com.jerry.ticketing.global.exception.MemberErrorCode;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import com.jerry.ticketing.member.domain.port.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @InjectMocks
    private MemberQueryService memberQueryService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private Member mockMember;


    @Test
    @DisplayName("memberId로 Member 객체를 가져올 수 있다.")
    void getByIdMember() {
        // given
        long memberId = 1L;
        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));

        // when
        Member member = memberQueryService.getMemberById(memberId, Function.identity());

        // then
        verify(memberRepository).findById(memberId);
        assertThat(member).isNotNull();
    }


    @Test
    @DisplayName("멤버 Id로 MemberDto 객체를 가져올 수 있다.")
    void getDtoByIdMember() {
        // given
        long memberId = 1L;

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mockMember));

        // when
        MemberDto memberDto = memberQueryService.getMemberById(memberId, MemberDto::from);

        // then
        verify(memberRepository).findById(memberId);
        assertThat(memberDto).isNotNull();
    }


    @Test
    @DisplayName("멤버 Id가 없는 경우 Member 에러 코드를 발생한다.")
    void getByIdMemberShouldThrowExceptionWhenIdNotExists() {
        // given
        long memberId = 999L;
        given(memberRepository.findById(memberId)).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> memberQueryService.getMemberById(memberId, Function.identity()))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", MemberErrorCode.MEMBER_NOT_FOUND);
    }


    @Test
    @DisplayName("이메일 Id가 없는 경우 Member 객체를 가져올 수 있다.")
    void getByEmailMemberWhenEmailNotExists() {
        // given
        String memberEmailId = "member-email-id-123";
        given(memberRepository.findByEmail(memberEmailId)).willReturn(Optional.of(mockMember));

        // when
        Member member = memberQueryService.getMemberByEmail(memberEmailId, Function.identity());

        // then
        verify(memberRepository).findByEmail(memberEmailId);
        assertThat(member).isNotNull();
    }


    @Test
    @DisplayName("이메일 Id가 없는 경우 MemberDto 객체를 가져올 수 있다.")
    void getDtoByEmailMemberWhenEmailNotExists() {
        // given
        String memberEmailId = "member-email-id-123";
        given(memberRepository.findByEmail(memberEmailId)).willReturn(Optional.of(mockMember));

        // when
        MemberDto memberDto = memberQueryService.getMemberByEmail(memberEmailId, MemberDto::from);

        // then
        verify(memberRepository).findByEmail(memberEmailId);
        assertThat(memberDto).isNotNull();
    }


    @Test
    @DisplayName("이메일 Id가 없는 경우 Member 에러 코드를 발생한다.")
    void getByEmailMemberShouldThrowExceptionWhenEmailNotExists() {
        // given
        String memberEmailId = "member-email-id-99999999";
        given(memberRepository.findByEmail(memberEmailId)).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> memberQueryService.getMemberByEmail(memberEmailId, Function.identity()))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", MemberErrorCode.MEMBER_NOT_FOUND);
    }


    @Test
    @DisplayName("구글 Id(Provider)가 존재하면, 구글 정보를 업데이트 후 MemberDto를 반환한다.")
    void oauthLoginMemberShouldUpdateGoogleInfoWhenProviderExists() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        GoogleUserInfo googleUserInfo = GoogleUserInfo.of("ProviderId-123", "name-123", "email-123", "picture-123");
        given(memberRepository.findByProviderAndProviderId(Provider.GOOGLE, googleUserInfo.getId())).willReturn(Optional.of(mockMember));

        // when
        MemberDto memberDto = memberQueryService.updateGoogleInfo(googleUserInfo, dateTime);

        // then
        verify(mockMember).updateGoogleInfo(
                eq(googleUserInfo.getName()), eq(googleUserInfo.getPicture()), any(OffsetDateTime.class)
        );

        verify(memberRepository, never()).save(any(Member.class));

        assertThat(memberDto).isNotNull();
    }


    @Test
    @DisplayName("구글 Id(Provider)가 존재하지 않으면, 새로운 멤버를 생성 후 MemberDto를 반환한다.")
    void oauthLoginMemberShouldCreateNewWhenProviderNotExists() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("name-123", "email-123", "ProviderId-123", "picture-123", dateTime);
        GoogleUserInfo googleUserInfo = GoogleUserInfo.of("ProviderId-123", "name-123", "email-123", "picture-123");

        given(memberRepository.findByProviderAndProviderId(Provider.GOOGLE, googleUserInfo.getId())).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(member);

        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);

        // when
        MemberDto memberDto = memberQueryService.updateGoogleInfo(googleUserInfo, dateTime);

        // then
        verify(memberRepository).save(memberCaptor.capture());
        Member capturedMember = memberCaptor.getValue();

        assertThat(capturedMember)
                .extracting("name", "email", "providerId", "profileImage", "createdAt")
                .containsExactlyInAnyOrder(memberDto.getName(), memberDto.getEmail(), memberDto.getProviderId(), memberDto.getProfileImage(), dateTime);
    }


}