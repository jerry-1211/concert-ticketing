package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.global.auth.jwt.JwtTokenProvider;
import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ReservationFacade {

    private final MemberQueryService memberQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    public Reservation create(CreateReservationDto.Request request) {
        OffsetDateTime dateTime = OffsetDateTime.now();

        String userEmail = jwtTokenProvider.getUserEmailFromToken(request.getToken());
        Member member = memberQueryService.getMemberByEmail(userEmail, Function.identity());

        return Reservation.of(
                member.getId(), request.getConcertId(),
                request.getOrderName(), dateTime, request.getExpireAt(),
                request.getTotalAmount(), request.getQuantity());
    }
}
