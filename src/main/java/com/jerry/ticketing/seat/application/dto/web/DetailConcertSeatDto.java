package com.jerry.ticketing.seat.application.dto.web;

import com.jerry.ticketing.seat.application.dto.domain.ConcertSeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SeatDto;
import com.jerry.ticketing.seat.application.dto.domain.SectionDto;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
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
        return new DetailConcertSeatDto(
                seat.getSeatId(),
                seat.getSeatRow(),
                seat.getSeatType(),

                concertSeat.getConcertSeatId(),
                concertSeat.getTotalAmount(),
                concertSeat.getStatus(),

                section.getZone(),
                section.getRemainingSeats()
        );
    }


}
