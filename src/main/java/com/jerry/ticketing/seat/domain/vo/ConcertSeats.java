package com.jerry.ticketing.seat.domain.vo;

import com.jerry.ticketing.seat.domain.ConcertSeat;

import java.time.OffsetDateTime;
import java.util.List;

public class ConcertSeats {
    private final List<ConcertSeat> concertSeats;

    public ConcertSeats(List<ConcertSeat> concertSeats) {
        this.concertSeats = concertSeats;
    }

    public List<ConcertSeat> item() {
        return this.concertSeats;
    }

    public static ConcertSeats from(List<ConcertSeat> concertSeats) {
        return new ConcertSeats(concertSeats);
    }

    public void occupy(Long memberId, OffsetDateTime now) {
        this.concertSeats.forEach(v -> {
            v.occupy(memberId, now);
        });
    }

    public void available() {
        this.concertSeats.forEach(ConcertSeat::release);
    }

    public boolean isNotSame(ConcertSeats concertSeats, int seatCount) {
        return this.concertSeats.size() != seatCount;
    }

    public List<Long> getSeatIds() {
        return concertSeats.stream()
                .map(ConcertSeat::getSeatId)
                .distinct()
                .toList();
    }

    public List<Long> getSectionIds() {
        return concertSeats.stream()
                .map(ConcertSeat::getSectionId)
                .distinct()
                .toList();
    }

    public static int calculateTotalAmount(ConcertSeats concertSeats) {
        int amount = concertSeats.item().get(0).getAmount();
        return amount * concertSeats.item().size();
    }


}

