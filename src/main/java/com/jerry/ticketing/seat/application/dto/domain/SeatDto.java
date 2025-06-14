package com.jerry.ticketing.seat.application.dto.domain;


import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatDto {

    private Long seatId;
    private String seatRow;
    private int number;
    private SeatType seatType;

    public static SeatDto from(Seat seat) {
        return SeatDto.builder()
                .seatId(seat.getId())
                .seatRow(seat.getSeatRow())
                .number(seat.getNumber())
                .seatType(seat.getSeatType())
                .build();
    }

}
