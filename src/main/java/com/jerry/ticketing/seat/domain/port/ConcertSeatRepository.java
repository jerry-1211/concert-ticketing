package com.jerry.ticketing.seat.domain.port;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface ConcertSeatRepository {
    ConcertSeat save(ConcertSeat concertSeat);

    List<ConcertSeat> saveAll(List<ConcertSeat> concertSeats);

    List<ConcertSeat> findByConcertId(Long id);

    List<ConcertSeat> findByExpireAtBeforeAndStatus(OffsetDateTime expireTime, ConcertSeatStatus status);

    List<ConcertSeat> findByConcertIdAndSeatIdIn(Long concertId, List<Long> seatIds);

    List<ConcertSeat> findByIdIn(List<Long> ids);

    List<ConcertSeat> findByJoinConditions(Long concertId, String zone, String row);

}
