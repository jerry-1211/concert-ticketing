package com.jerry.ticketing.reservation.application;

import com.jerry.ticketing.global.auth.jwt.JwtTokenProvider;
import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    @Mock
    private Member mockMember;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private ReservationFacade reservationFacade;


    @Test
    @DisplayName("파라미터(reservation 생성 요청)과 member Id를 기반으로 예약을 만든다.")
    void ReservationCreat2eTest() {
        // given
        String email = "kj@nv.com";

        OffsetDateTime dateTime = OffsetDateTime.now();
        CreateReservationDto.Request request = CreateReservationDto.Request.of(
                "token-123", 1L, "orderName-123", dateTime, 10_000, 3
        );

        given(jwtTokenProvider.getUserEmailFromToken(request.getToken())).willReturn(email);
        given(memberQueryService.getMemberByEmail(email, Function.identity())).willReturn(mockMember);

        given(mockMember.getId()).willReturn(1L);

        Reservation expectedReservation = Reservation.of(mockMember.getId(), request.getConcertId(), request.getOrderName(), dateTime,
                request.getExpireAt(), request.getTotalAmount(), request.getQuantity());

        // when
        Reservation reservation = reservationFacade.create(request);

        // then
        assertThat(reservation)
                .extracting("memberId", "concertId", "orderName", "totalAmount", "quantity")
                .containsExactlyInAnyOrder(
                        expectedReservation.getMemberId(), expectedReservation.getConcertId(),
                        expectedReservation.getOrderName(), expectedReservation.getTotalAmount(),
                        expectedReservation.getQuantity());
    }


}