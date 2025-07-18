package com.jerry.ticketing.seat.application.concertseat.web;

import com.jerry.ticketing.seat.application.concertseat.dto.ConcertSeatDto;
import com.jerry.ticketing.seat.application.seat.dto.SeatDto;
import com.jerry.ticketing.seat.application.section.dto.SectionDto;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class DetailedConcertSeatDto {
    private Long seatId;
    private String row;
    private SeatType seatType;

    private Long concertSeatId;
    private int totalAmount;
    private ConcertSeatStatus status;

    private String zone;
    private int remainingSeats;

    public static DetailedConcertSeatDto from(SeatDto seat, ConcertSeatDto concertSeat, SectionDto section) {
        return DetailedConcertSeatDto.builder()
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
