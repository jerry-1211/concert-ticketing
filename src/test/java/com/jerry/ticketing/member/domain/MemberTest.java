package com.jerry.ticketing.member.domain;

import com.jerry.ticketing.member.application.dto.UpdateProfile;
import com.jerry.ticketing.member.domain.enums.MemberRole;
import com.jerry.ticketing.member.domain.enums.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("멤버의 구글 로그인 정보를 만든다.")
    void createMemberGoogleLoginInfo() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        // when // then
        assertThat(member)
                .extracting("name", "email", "provider",
                        "providerId", "profileImage", "memberRole", "createdAt", "updatedAt")
                .containsExactlyInAnyOrder("jerim", "kj@gmail.com", Provider.GOOGLE,
                        "1234", "Img", MemberRole.USER, dateTime, dateTime
                );
    }

    @Test
    @DisplayName("멤버의 구글 로그인 정보를 업데이트 한다.")
    void updateMemberGoogleLoginInfo() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        String name = "jerry";
        String profileImage = "Image";
        OffsetDateTime updateDateTime = OffsetDateTime.now().plusHours(1);

        // when
        member.updateGoogleInfo(name, profileImage, updateDateTime);

        // then
        assertThat(member)
                .extracting("name", "profileImage", "updatedAt")
                .containsExactlyInAnyOrder(name, profileImage, updateDateTime);
    }

    @Test
    @DisplayName("멤버의 구글 로그인 정보를 요청에 따라 업데이트 한다.")
    void updateMemberInfoByRequest() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        String updateName = "jerry";
        String updatePhoneNumber = "010-111-1111";
        UpdateProfile.Request request = UpdateProfile.Request.of(updateName, updatePhoneNumber);

        // when
        member.updateProfile(request);

        // then
        assertThat(member)
                .extracting("name", "phoneNumber")
                .containsExactlyInAnyOrder(updateName, updatePhoneNumber);
    }

    @Test
    @DisplayName("멤버의 구글 로그인 정보 업데이트에 null 요청시, 기존 값들이 유지된다.")
    void maintainExistingValuesWhenUpdateMemberGoogleLoginInfoWithNull() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        String updateName = "jerry";
        String updatePhoneNumber = "010-111-1111";
        UpdateProfile.Request request = UpdateProfile.Request.of(updateName, updatePhoneNumber);
        member.updateProfile(request);

        // when
        member.updateProfile(UpdateProfile.Request.of(null, null));


        // then
        assertThat(member)
                .extracting("name", "phoneNumber")
                .containsExactlyInAnyOrder(updateName, updatePhoneNumber);
    }

}