package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.reservation.ReservationItem;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcertSeat {

    // 콘서트 좌석 id
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘서트 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    // 좌석 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // 예약 아이템 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_item_id")
    private ReservationItem reservationItem;

    // 콘서트 좌석 가격
    private int price;

    // 콘서트 좌석 상태
    @Enumerated(EnumType.STRING)
    @Setter
    private ConcertSeatStatus status;

    // 좌석 선점 멤버 ID
    @Setter
    private Long blockedBy;

    // 좌석 선점 시작 시간
    @Setter
    private LocalDateTime blockedAt;

    // 좌석 선점 시작 만료 시간
    @Setter
    private LocalDateTime blockedExpireAt;

    // 좌석 예약 가능 여부
    public boolean isAvailable(){
        return this.status == ConcertSeatStatus.AVAILABLE;
    }

}
