package com.jerry.ticketing.member.application;

import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.application.dto.MyPageDto;
import com.jerry.ticketing.member.application.dto.MyReservationListDto;
import com.jerry.ticketing.member.application.dto.UpdateProfile;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.domain.enums.Provider;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private ReservationQueryService reservationQueryService;

    @Mock
    private ConcertQueryService concertQueryService;

    @InjectMocks
    private MyPageService myPageService;


    @Test
    @DisplayName("이메일로 멤버를 찾아 멤버의 마이페이지 정보를 가져온다.")
    void getMemberMyPageInfoByEmail() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        String name = "jerim";
        String email = "kj@gmail.com";
        String providerId = "1234";
        String profileImage = "Img";
        Member member = Member.ofGoogle(name, email, providerId, profileImage, dateTime);

        given(memberQueryService.getMemberByEmail(email, Function.identity())).willReturn(member);

        // when
        MyPageDto myPage = myPageService.getMyPage(email);

        // then
        assertThat(myPage)
                .extracting("name", "email", "provider", "profileImage", "createdAt", "updatedAt")
                .containsExactlyInAnyOrder(name, email, Provider.GOOGLE, profileImage, dateTime, dateTime);
    }

    @Test
    @DisplayName("이메일로 멤버를 찾아 멤버의 예약 정보들을 가져온다.")
    void getMemberReservationListByEmail() {
        // given
        String title = "Cold Play 내한";
        String venue = "서울 상암동";
        int totalAmount1 = 30_000;
        int totalAmount2 = 20_000;
        OffsetDateTime dateTime = OffsetDateTime.now();

        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);

        Reservation reservation1 = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, totalAmount1, 3);
        Reservation reservation2 = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, totalAmount2, 2);

        Concert concert = Concert.of(title, dateTime, venue, 10_000, "Cold Play의 세번째 내한 방문 !", 3);

        given(memberQueryService.getMemberByEmail("kj@gmail.com", Function.identity())).willReturn(member);
        given(reservationQueryService.getReservation(member.getId())).willReturn(List.of(reservation1, reservation2));
        given(concertQueryService.getConcertById(anyLong(), any(Function.class))).willReturn(concert);

        // when
        List<MyReservationListDto> myReservation = myPageService.getMyReservation("kj@gmail.com");

        // then
        assertThat(myReservation)
                .hasSize(2)
                .extracting("title", "dateTime", "venue", "totalAmount", "createdAt")
                .containsExactlyInAnyOrder(
                        tuple(title, dateTime, venue, totalAmount1, dateTime),
                        tuple(title, dateTime, venue, totalAmount2, dateTime)
                );
    }

    @Test
    @DisplayName("매개변수(이메일과 업데이트 프로필 요청)로 업데이트 프로필 메서드를 호출한다. ")
    void updateProfileByEmailAndUpdateRequest() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Member member = Member.ofGoogle("jerim", "kj@gmail.com", "1234", "Img", dateTime);
        UpdateProfile request = UpdateProfile.of("jerry", "010-111-1111");

        MyPageDto myPageDto = MyPageDto.from(member);

        given(memberQueryService.updateProfile(anyString(), any(UpdateProfile.class))).willReturn(member);

        // when
        MyPageDto myPage = myPageService.updateMyPage("email-123", request);

        // then
        assertThat(myPage)
                .extracting("name", "email", "profileImage", "createdAt")
                .containsExactlyInAnyOrder(myPageDto.getName(), myPageDto.getEmail(), myPageDto.getProfileImage(), myPage.getCreatedAt());

    }


}