package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.seat.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    // 좌석 id
    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좌석 열
    @Column(name = "seat_row")
    private String seatRow;

    // 좌석 번호
    private int number;

    // 좌석 타입
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private Seat(String seatRow, int number, SeatType seatType) {
        this.seatRow = seatRow;
        this.number = number;
        this.seatType = seatType;
    }

    public static Seat createSeat(String seatRow, int number, SeatType seatType){
        return new Seat(seatRow, number, seatType);
    }

}
