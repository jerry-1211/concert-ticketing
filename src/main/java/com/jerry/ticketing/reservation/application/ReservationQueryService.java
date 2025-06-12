package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public <T> T findReservationById(Long reservationId, Function<Reservation, T> mapper) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        return mapper.apply(reservation);

    }

}
