package com.jerry.ticketing.seat.domain;


import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSeat {

    public static final int BLOCKING_TIMEOUT_MINUTES = 15;
    public static final int BLOCKING_CHECK_INTERVAL_SECONDS = 30000;

    // 콘서트 좌석 id
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘서트 id
    @Column(name = "concert_id")
    private Long concertId;


    // 좌석 id
    @Column(name = "seat_id")
    private Long seatId;

    // 구역 id
    @Column(name = "section_id")
    private Long sectionId;


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

    private ConcertSeat(Long concertId, Long seatId, Long sectionId, int price) {
        this.concertId = concertId;
        this.seatId = seatId;
        this.sectionId = sectionId;
        this.price = price;
        this.status = ConcertSeatStatus.AVAILABLE;

    }

    // 콘서트 좌석 생성
    public static ConcertSeat creatConcertSeat(Long concertId, Long seatId, Long sectionId, int price) {
        return new ConcertSeat(concertId, seatId, sectionId, price);
    }


    // 콘서트 좌석 초기화
    public void initConcertSeat() {
        this.status = ConcertSeatStatus.AVAILABLE;
        this.blockedBy = null;
        this.blockedAt = null;
        this.blockedExpireAt = null;

    }

    // 좌석 선점
    public void blockConcertSeat(Long memberId) {
        this.status = ConcertSeatStatus.BLOCKED;
        this.blockedBy = memberId;
        this.blockedAt = OffsetDateTime.now();
        this.blockedExpireAt = OffsetDateTime.now().plusMinutes(BLOCKING_TIMEOUT_MINUTES);
    }

    // 좌석 확정
    public void confirmConcertSeat() {
        this.status = ConcertSeatStatus.RESERVED;
        this.blockedExpireAt = OffsetDateTime.now().plusYears(10);
    }


    // 좌석 예약 가능 여부
    public boolean isAvailable() {
        return this.status == ConcertSeatStatus.AVAILABLE;
    }

    public boolean isNotAvailable() {
        return !isAvailable();
    }

    // TODO: 가격 계산 (추후 일급 객체로 리팩토링 필요)
    public static int calculateTotalPrice(List<ConcertSeat> concertSeats) {
        int price = concertSeats.get(0).getPrice();
        return price * concertSeats.size();
    }
}
