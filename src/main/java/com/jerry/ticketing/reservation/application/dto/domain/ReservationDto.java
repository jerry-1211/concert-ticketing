package com.jerry.ticketing.reservation.application.dto.domain;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ReservationDto {

    private Long reservationId;
    private Long memberId;
    private Long concertId;
    private int totalAmount;
    private ReservationStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
    private int quantity;
    private String orderId;
    private String orderName;

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .reservationId(reservation.getId())
                .memberId(reservation.getMemberId())
                .concertId(reservation.getConcertId())
                .totalAmount(reservation.getTotalAmount())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .expiresAt(reservation.getExpiresAt())
                .quantity(reservation.getQuantity())
                .orderId(reservation.getOrderId())
                .orderName(reservation.getOrderName())
                .build();
    }
}
