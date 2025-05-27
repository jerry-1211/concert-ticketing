package com.jerry.ticketing.domain.concert;


import java.util.Objects;

public class ConcertProxy extends Concert{

    private Concert concert;

    public ConcertProxy(Concert concert) {

        if(Objects.isNull(concert)) {
            this.concert = concert;
        }
    }


    String getTitle() {

        return this.concert.getTitle();
    }
}
