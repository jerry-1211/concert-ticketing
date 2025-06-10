package com.jerry.ticketing.seat.infrastructure.repository;

import com.jerry.ticketing.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {

}
