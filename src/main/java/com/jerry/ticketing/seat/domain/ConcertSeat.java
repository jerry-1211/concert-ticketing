package com.jerry.ticketing.seat.domain;


import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSeat {

    public static final int BLOCKING_TIMEOUT_MINUTES = 15;
    public static final int BLOCKING_CHECK_INTERVAL_SECONDS = 30000;
    public static final int CONCERT_SEAT_EXPIRE_AT = 10;

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
    private int amount;

    // 콘서트 좌석 상태
    @Enumerated(EnumType.STRING)
    private ConcertSeatStatus status;

    // 좌석 선점 멤버 ID
    private Long blockedBy;

    // 좌석 선점 시작 시간
    private OffsetDateTime blockedAt;

    // 좌석 선점 시작 만료 시간
    private OffsetDateTime expireAt;

    private ConcertSeat(Long concertId, Long seatId, Long sectionId, int amount) {
        this.concertId = concertId;
        this.seatId = seatId;
        this.sectionId = sectionId;
        this.amount = amount;
        this.status = ConcertSeatStatus.AVAILABLE;
    }


    public static ConcertSeat of(Long concertId, Long seatId, Long sectionId, int amount) {
        return new ConcertSeat(concertId, seatId, sectionId, amount);
    }


    public boolean isAvailable() {
        return this.status == ConcertSeatStatus.AVAILABLE;
    }


    public boolean isNotAvailable() {
        return !isAvailable();
    }


    public void block() {
        this.status = ConcertSeatStatus.BLOCKED;
    }


    public void occupy(Long memberId, OffsetDateTime now) {
        this.block();
        this.blockedBy = memberId;
        this.blockedAt = now;
        this.expireAt = now.plusMinutes(BLOCKING_TIMEOUT_MINUTES);
    }


    public void confirm() {
        this.status = ConcertSeatStatus.RESERVED;
        this.expireAt = blockedAt.plusYears(CONCERT_SEAT_EXPIRE_AT);
    }


    public void release() {
        this.status = ConcertSeatStatus.AVAILABLE;
        this.blockedBy = null;
        this.blockedAt = null;
        this.expireAt = null;
    }

}
