package com.jerry.ticketing.reservation.infrastructure.repository;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long id);

    Optional<Reservation> findByOrderId(String orderId);

    List<Reservation> findByExpiresAtBeforeAndStatus(OffsetDateTime expiresAtBefore, ReservationStatus status);

}
