package com.jerry.ticketing.Repository.seat;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat,Long> {
    List<ConcertSeat> findBySeatId(Long id);

}
