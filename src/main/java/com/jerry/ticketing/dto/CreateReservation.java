package com.jerry.ticketing.dto;

import com.jerry.ticketing.domain.reservation.Reservation;
import lombok.*;

import java.time.OffsetDateTime;

public class CreateReservation {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long memberId;

        private Long concertId;

        private String orderName;

        private OffsetDateTime expireAt;

        private int totalPrice;

        private int amount;


    }



    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private String orderName;

        private int totalPrice;

//        private Long memberId;

//        private Long concertId;

        private Long reservationId;

        public static CreateReservation.Response from(Reservation reservation){
            return Response.builder()
                        .orderName(reservation.getOrderName())
                        .totalPrice(reservation.getTotalPrice())
                        .reservationId(reservation.getId())
                        .build();
        }

    }
}
