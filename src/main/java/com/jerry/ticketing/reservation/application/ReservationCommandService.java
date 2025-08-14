package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
import com.jerry.ticketing.reservation.domain.vo.Reservations;
import com.jerry.ticketing.reservation.util.OrderIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationQueryService reservationQueryService;
    private final ReservationFacade reservationFacade;
    private final OrderIdGenerator orderIdGenerator;

    @Transactional
    public CreateReservationDto.Response create(CreateReservationDto.Request request) {
        Reservation reservation = reservationFacade.create(request);
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
        Reservations reservations = Reservations.from(
                reservationRepository.findByExpiresAtBeforeAndStatus(now, ReservationStatus.PENDING)
        );
        reservations.cancel();
    }


    @Transactional
    public ReservationDto updateOrderId(Long reservationId) {
        Reservation reservation = reservationQueryService.getReservation(reservationId, Function.identity());
        String orderId = orderIdGenerator.generate(reservationId);
        reservation.updateOrderId(orderId);
        return ReservationDto.from(reservation);
    }

}
