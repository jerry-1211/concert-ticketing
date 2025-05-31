package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.reservation.ReservationItem;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 구역 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    // 예약 아이템 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_item_id")
    private ReservationItem reservationItem;

    // 콘서트 좌석별 가격
    private int price;

    // 콘서트 좌석 상태
    @Enumerated(EnumType.STRING)
    private ConcertSeatStatus status;

    // 좌석 선점 멤버 ID
    private Long blockedBy;

    // 좌석 선점 시작 시간
    private OffsetDateTime blockedAt;

    // 좌석 선점 시작 만료 시간
    private OffsetDateTime blockedExpireAt;

    private ConcertSeat(Concert concert, Seat seat, Section section, int price) {
        this.concert = concert;
        this.seat = seat;
        this.section = section;
        this.price = price;
        this.status = ConcertSeatStatus.AVAILABLE;

    }

    // 콘서트 좌석 생성
    public static ConcertSeat creatConcertSeat(Concert concert, Seat seat, Section section, int price) {
        return new ConcertSeat(concert, seat, section, price);
    }


    // 콘서트 좌석 초기화
    public void initConcertSeat() {
        this.status = ConcertSeatStatus.AVAILABLE;
        this.blockedBy = null;
        this.blockedAt = null;
        this.blockedExpireAt = null;

    }

    // 좌석 선점
    public void blockConcertSeat(Long memberId){
        final int BLOCKING_TIMEOUT_MINUTES = 15;

        this.status =  ConcertSeatStatus.BLOCKED;
        this.blockedBy = memberId;
        this.blockedAt = OffsetDateTime.now();
        this.blockedExpireAt = OffsetDateTime.now().plusMinutes(BLOCKING_TIMEOUT_MINUTES);

    }


    // 좌석 예약 가능 여부
    public boolean isAvailable(){
        return this.status == ConcertSeatStatus.AVAILABLE;
    }

    public boolean isNotAvailable(){
        return !isAvailable();
    }


}
