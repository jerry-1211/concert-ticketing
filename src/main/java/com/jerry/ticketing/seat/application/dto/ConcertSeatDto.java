package com.jerry.ticketing.seat.application.dto;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ConcertSeatDto {

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

        public static ConcertSeatDto.Response from (ConcertSeat concertSeat){
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
