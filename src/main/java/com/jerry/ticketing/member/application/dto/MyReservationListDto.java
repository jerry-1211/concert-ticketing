package com.jerry.ticketing.member.application.dto;


import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;


@Getter
@Builder
public class MyReservationListDto {

    private String title;
    private OffsetDateTime dateTime;
    private String venue;

    private String orderId;
    private int totalAmount;
    private ReservationStatus status;
    private OffsetDateTime createdAt;


    public static MyReservationListDto from(Reservation reservation, Concert concert) {
        return MyReservationListDto.builder()
                .title(concert.getTitle())
                .dateTime(concert.getDateTime())
                .venue(concert.getVenue())
                .orderId(reservation.getOrderId())
                .totalAmount(reservation.getTotalAmount())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }


}
