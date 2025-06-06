package com.jerry.ticketing.seat.domain;

import java.util.List;

public class ConcertSeats {
    private final List<ConcertSeat> concertSeats;

    public ConcertSeats(List<ConcertSeat> concertSeats) {
        this.concertSeats = concertSeats;
    }

    public List<ConcertSeat> item(){
        return this.concertSeats;
    }

    public static ConcertSeats from(List<ConcertSeat> concertSeats){
        return new ConcertSeats(concertSeats);
    }

    public void block(Long memberId){
        this.concertSeats.forEach(v->{
            v.blockConcertSeat(memberId);});
    }

    public void available(){
        this.concertSeats.forEach(ConcertSeat::initConcertSeat);
    }

    public boolean isNotSame(List<Long> ConcertSeatIds){
        return this.concertSeats.size() != ConcertSeatIds.size();
    }



}

