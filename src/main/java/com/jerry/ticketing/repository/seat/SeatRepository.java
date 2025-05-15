package com.jerry.ticketing.repository.seat;

import com.jerry.ticketing.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Long> {

}
