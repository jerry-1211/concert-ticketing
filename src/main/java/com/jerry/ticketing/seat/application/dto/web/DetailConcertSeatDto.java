package com.jerry.ticketing.seat.application.dto.web;

import com.jerry.ticketing.seat.application.dto.domain.ConcertSeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SectionDto;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class DetailConcertSeatDto {
    private Long seatId;
    private String row;
    private SeatType seatType;

    private Long concertSeatId;
    private int totalAmount;
    private ConcertSeatStatus status;

    private String zone;
    private int remainingSeats;

    public static DetailConcertSeatDto from(SeatDto seat, ConcertSeatDto concertSeat, SectionDto section) {
        return DetailConcertSeatDto.builder()
                .seatId(seat.getSeatId())
                .row(seat.getSeatRow())
                .seatType(seat.getSeatType())
                .concertSeatId(concertSeat.getConcertSeatId())
                .totalAmount(concertSeat.getTotalAmount())
                .status(concertSeat.getStatus())
                .zone(section.getZone())
                .remainingSeats(section.getRemainingSeats())
                .build();
    }

}
