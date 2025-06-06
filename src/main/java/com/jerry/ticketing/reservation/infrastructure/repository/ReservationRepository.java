package com.jerry.ticketing.reservation.infrastructure.repository;

import com.jerry.ticketing.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMemberId(Long id);
    Optional<Reservation> findByOrderId(String orderId);
}
