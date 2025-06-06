package com.jerry.ticketing.dto;


import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.domain.seat.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ConcertSeatSelect {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private Long seatId;
        private String zone;
        private String row;
        private Long seatNumber;
        private SeatType seatType;
        private int price;
        private ConcertSeatStatus status;

        public static ConcertSeatSelect.Response from (ConcertSeat concertSeat){
           return Response.builder()
                        .seatId(concertSeat.getId())
                        .zone(String.valueOf(concertSeat.getSection().getZone()))
                        .row(String.valueOf(concertSeat.getSeat().getSeatRow()))
                        .seatNumber(concertSeat.getSeat().getId())
                        .seatType(concertSeat.getSeat().getSeatType())
                        .price(concertSeat.getPrice())
                        .status(concertSeat.getStatus())
                        .build();
        }


    }
}
