package com.jerry.ticketing.seat.infrastructure.repository.seat;

import com.jerry.ticketing.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatJpaRepository extends JpaRepository<Seat, Long> {
}
