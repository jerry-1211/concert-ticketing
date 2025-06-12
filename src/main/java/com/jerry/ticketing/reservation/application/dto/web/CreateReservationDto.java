package com.jerry.ticketing.reservation.application.dto.web;

import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.*;

import java.time.OffsetDateTime;

public class CreateReservationDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {

        private Long memberId;
        private Long concertId;
        private String orderName;
        private OffsetDateTime expireAt;
        private int totalAmount;
        private int quantity;

    }


    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private String orderName;
        private int totalAmount;
        private Long reservationId;

        public static CreateReservationDto.Response from(Reservation reservation) {
            return Response.builder()
                    .orderName(reservation.getOrderName())
                    .totalAmount(reservation.getTotalAmount())
                    .reservationId(reservation.getId())
                    .build();
        }

    }
}
