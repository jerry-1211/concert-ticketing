package com.jerry.ticketing.reservation.application.dto.domain;

import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
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
        return new ReservationDto(
                reservation.getId(),
                reservation.getMemberId(),
                reservation.getConcertId(),
                reservation.getTotalAmount(),
                reservation.getStatus(),
                reservation.getCreatedAt(),
                reservation.getExpiresAt(),
                reservation.getQuantity(),
                reservation.getOrderId(),
                reservation.getOrderName()
        );
    }
}
