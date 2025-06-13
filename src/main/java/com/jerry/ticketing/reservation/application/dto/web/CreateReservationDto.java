package com.jerry.ticketing.reservation.application.dto.web;

import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.*;

import java.time.OffsetDateTime;

public class CreateReservationDto {

    @Getter
    @NoArgsConstructor
    public static class Request {

        private Long memberId;
        private Long concertId;
        private String orderName;
        private OffsetDateTime expireAt;
        private int totalAmount;
        private int quantity;

        public Request(Long memberId, Long concertId, String orderName, OffsetDateTime expireAt, int totalAmount, int quantity) {
            this.memberId = memberId;
            this.concertId = concertId;
            this.orderName = orderName;
            this.expireAt = expireAt;
            this.totalAmount = totalAmount;
            this.quantity = quantity;
        }
    }


    @Getter
    @Builder
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
