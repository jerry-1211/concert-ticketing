package com.jerry.ticketing.repository.reservation;

import com.jerry.ticketing.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMemberId(Long id);
    Optional<Reservation> findByOrderId(String orderId);
}
