package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final MemberQueryService memberQueryService;
    private final ConcertQueryService concertQueryService;

    @Transactional
    public CreateReservationDto.Response createReservation(CreateReservationDto.Request request) {
        Member member = memberQueryService.findMemberById(request.getMemberId(), Function.identity());
        Concert concert = concertQueryService.findConcertById(request.getConcertId(), Function.identity());

        Reservation reservation = Reservation.createReservation(
                member.getId(), concert.getId(),
                request.getOrderName(), request.getExpireAt(), request.getTotalAmount(), request.getQuantity());

        reservationRepository.save(reservation);

        return CreateReservationDto.Response.from(reservation);

    }


    @Transactional
    public void confirmReservation(String orderId) {
        Reservation reservation = reservationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        reservation.confirmReservation();
    }


    @Transactional
    public void releaseExpiredReservation() {
        OffsetDateTime now = OffsetDateTime.now();
        List<Reservation> reservations = reservationRepository.findByExpiresAtBeforeAndStatus(now, ReservationStatus.PENDING);


        // TODO: 일급 객체로 바꾸기
        reservations.forEach(Reservation::cancelReservation);

    }


}
