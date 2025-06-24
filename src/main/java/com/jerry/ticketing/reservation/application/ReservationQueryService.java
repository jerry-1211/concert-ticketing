package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public <T> T getReservation(Long reservationId, Function<Reservation, T> mapper) {
        return mapper.apply(reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND)));
    }


    @Transactional(readOnly = true)
    public List<Reservation> getReservation(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

}
