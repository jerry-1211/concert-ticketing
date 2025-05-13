package com.jerry.ticketing.Repository.reservation;

import com.jerry.ticketing.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByMemberId(Long id);
}
