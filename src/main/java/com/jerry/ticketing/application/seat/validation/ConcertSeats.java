package com.jerry.ticketing.application.seat.validation;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;

import java.util.List;

public class ConcertSeats {

    private final List<ConcertSeat> concertSeats;

    private ConcertSeats(List<ConcertSeat> concertSeats) {
        this.concertSeats = concertSeats;
    }

    public static ConcertSeats from(List<ConcertSeat> concertSeats) {
        return new ConcertSeats(concertSeats);
    }

    public boolean isNotSame(List<Long> seatIds) {
        return this.concertSeats.size() != seatIds.size();
    }

    public List<ConcertSeat> item() {
        return this.concertSeats;
    }

    public List<Long> ids() {
        return this.concertSeats.stream()
                .map(ConcertSeat::getId)
                .toList();
    }

    public void available() {
        this.concertSeats.forEach(ConcertSeat::initSeat);
    }

    public void block(Long memberId) {
        this.concertSeats.forEach(v -> {v.blockSeat(memberId);});
    }
}
