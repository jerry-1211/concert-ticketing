package com.jerry.ticketing.seat.application.dto;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertSeatDto {

    private Long concertSeatId;
    private Long concertId;
    private Long seatId;
    private Long sectionId;
    private int price;
    private ConcertSeatStatus status;
    private Long blockedBy;
    private OffsetDateTime blockedAt;
    private OffsetDateTime blockedExpireAt;


    public static ConcertSeatDto from(ConcertSeat concertSeat) {
        return new ConcertSeatDto(
                concertSeat.getId(),
                concertSeat.getConcertId(),
                concertSeat.getSeatId(),
                concertSeat.getSectionId(),
                concertSeat.getPrice(),
                concertSeat.getStatus(),
                concertSeat.getBlockedBy(),
                concertSeat.getBlockedAt(),
                concertSeat.getBlockedExpireAt()
        );
    }


}
