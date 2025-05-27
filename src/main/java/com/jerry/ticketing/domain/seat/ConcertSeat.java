package com.jerry.ticketing.domain.seat;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.reservation.ReservationItem;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class ConcertSeat extends  BaseEntity{

    // 콘서트 좌석 id
    @Id
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
    private LocalDateTime blockedAt;

    // 좌석 선점 시작 만료 시간
    private LocalDateTime blockedExpireAt;

    // 좌석 예약 가능 여부
    public boolean isAvailable(){
        return this.status == ConcertSeatStatus.AVAILABLE;
    }

    public boolean isNotAvailable() {
        return !isAvailable();
    }

    public void blockSeat(Long memberId) {

         final int BLOCKING_TIMEOUT_MINUTES = 15;

        this.status = ConcertSeatStatus.BLOCKED;
        this.blockedBy = memberId;
        this.blockedAt = LocalDateTime.now();
        this.blockedExpireAt = LocalDateTime.now().plusMinutes(BLOCKING_TIMEOUT_MINUTES);
    }

    public void initSeat() {
        this.status = ConcertSeatStatus.AVAILABLE;
        this.blockedBy = null;
        this.blockedAt = null;
        this.blockedExpireAt = null;
    }
}

¡