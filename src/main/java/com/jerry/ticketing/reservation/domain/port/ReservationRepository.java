package com.jerry.ticketing.reservation.domain.port;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findByMemberId(Long id);

    Optional<Reservation> findByOrderId(String orderId);

    List<Reservation> findByExpiresAtBeforeAndStatus(OffsetDateTime expiresAtBefore, ReservationStatus status);
}

