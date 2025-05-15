package com.jerry.ticketing.repository.seat;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertSeatRepository extends JpaRepository<ConcertSeat,Long> {
    List<ConcertSeat> findBySeatId(Long id);

    List<ConcertSeat> findByConcertIdAndSeatIdIn(Long concertId, List<Long> seatIds);

    List<ConcertSeat> findByBlockedExpireAtBeforeAndStatus(LocalDateTime expireTime, ConcertSeatStatus status);
}
