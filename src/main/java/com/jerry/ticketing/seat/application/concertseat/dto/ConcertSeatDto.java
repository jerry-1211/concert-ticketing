package com.jerry.ticketing.seat.application.concertseat.dto;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ConcertSeatDto {

    private Long concertSeatId;
    private Long concertId;
    private Long seatId;
    private Long sectionId;
    private int totalAmount;
    private ConcertSeatStatus status;
    private Long blockedBy;
    private OffsetDateTime blockedAt;
    private OffsetDateTime blockedExpireAt;


    public static ConcertSeatDto from(ConcertSeat concertSeat) {
        return ConcertSeatDto.builder()
                .concertSeatId(concertSeat.getId())
                .concertId(concertSeat.getConcertId())
                .seatId(concertSeat.getSeatId())
                .sectionId(concertSeat.getSectionId())
                .totalAmount(concertSeat.getAmount())
                .status(concertSeat.getStatus())
                .blockedBy(concertSeat.getBlockedBy())
                .blockedAt(concertSeat.getBlockedAt())
                .blockedExpireAt(concertSeat.getBlockedExpireAt())
                .build();
    }


}
