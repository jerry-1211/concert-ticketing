package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;


    @Transactional(readOnly = true)
    public ReservationDto findReservationDtoById(Long reservationId) {
        return ReservationDto.from(reservationRepository.findById(reservationId)
                .orElseThrow());
    }

    @Transactional(readOnly = true)
    public Reservation findReservationEntityById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow();
    }


}
