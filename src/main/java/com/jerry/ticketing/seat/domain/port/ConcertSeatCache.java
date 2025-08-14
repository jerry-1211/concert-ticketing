package com.jerry.ticketing.seat.domain.port;

import com.jerry.ticketing.seat.domain.ConcertSeat;

import java.util.List;

public interface ConcertSeatCache {
    boolean areAllConcertSeatsNotCached(Long concertId, List<Long> concertSeatIds);

    String cacheConcertSeatOccupancy(ConcertSeat concertSeat);

    List<String> cacheConcertSeatOccupancies(List<ConcertSeat> concertSeats);

}
