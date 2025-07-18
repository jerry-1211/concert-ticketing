package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.global.auth.jwt.JwtTokenProvider;
import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.util.PaymentOrderIdGenerator;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
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
    private final ReservationQueryService reservationQueryService;
    private final MemberQueryService memberQueryService;
    private final ConcertQueryService concertQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public CreateReservationDto.Response create(CreateReservationDto.Request request) {
        OffsetDateTime dateTime = OffsetDateTime.now();

        String userEmail = jwtTokenProvider.getUserEmailFromToken(request.getToken());
        Member member = memberQueryService.getMemberByEmail(userEmail, Function.identity());
        Concert concert = concertQueryService.getConcertById(request.getConcertId(), Function.identity());

        Reservation reservation = Reservation.of(
                member.getId(), concert.getId(),
                request.getOrderName(), dateTime, request.getExpireAt(), request.getTotalAmount(), request.getQuantity());

        reservationRepository.save(reservation);

        return CreateReservationDto.Response.from(reservation);

    }


    @Transactional
    public void confirm(String orderId) {
        Reservation reservation = reservationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        reservation.confirmReservation();
    }


    @Transactional
    public void releaseExpiredReservations() {
        OffsetDateTime now = OffsetDateTime.now();
        List<Reservation> reservations = reservationRepository.findByExpiresAtBeforeAndStatus(now, ReservationStatus.PENDING);

        reservations.forEach(Reservation::cancelReservation);
    }

    @Transactional
    public ReservationDto updateOrderId(CreatePaymentDto.Request request) {
        Reservation reservation = reservationQueryService.getReservation(request.getReservationId(), Function.identity());
        String orderId = PaymentOrderIdGenerator.generate(reservation.getId());
        reservation.updateOrderId(orderId);
        return ReservationDto.from(reservation);
    }

}
