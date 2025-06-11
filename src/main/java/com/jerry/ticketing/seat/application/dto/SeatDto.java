package com.jerry.ticketing.seat.application.dto;


import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {

    private Long seatId;
    private String seatRow;
    private int number;
    private SeatType seatType;

    public static SeatDto from(Seat seat) {
        return new SeatDto(
                seat.getId(),
                seat.getSeatRow(),
                seat.getNumber(),
                seat.getSeatType()
        );
    }

}
