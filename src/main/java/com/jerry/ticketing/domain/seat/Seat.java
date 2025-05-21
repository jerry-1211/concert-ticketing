package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.seat.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

}
