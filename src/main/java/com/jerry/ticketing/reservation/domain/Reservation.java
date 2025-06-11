package com.jerry.ticketing.reservation.domain;


import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    public static final int RESERVATION_TIMEOUT_MINUTES = 2;
    public static final int PENDING_CHECK_INTERVAL_SECONDS = 30000;


    // 예약 id
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 멤버 id
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // 콘서트 id
    @Column(name = "concert_id", nullable = false)
    private Long concertId;

    // 예약 전체 가격
    @Column(nullable = false)
    private int totalPrice;

    //예약 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    //예약 만들어진 시점
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    //예약 만료 기한
    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    // 결제 티켓 숫자
    @Column(nullable = false)
    private int amount;

    @Column
    private String orderId;

    @Column
    private String orderName;

    private Reservation(Long memberId, Long concertId, int totalPrice, ReservationStatus status, OffsetDateTime createdAt, OffsetDateTime expiresAt, int amount) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.amount = amount;
    }

    public Reservation(Long memberId, Long concertId, String orderName, OffsetDateTime expiresAt, int totalPrice, int amount) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.orderName = orderName;
        this.expiresAt = expiresAt;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.status = ReservationStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public static Reservation createReservation(Long memberId, Long concertId,
                                                int totalPrice, ReservationStatus status,
                                                OffsetDateTime createdAt, OffsetDateTime expiresAt, int amount) {

        return new Reservation(memberId, concertId, totalPrice, status, createdAt, expiresAt, amount);

    }


    public static Reservation createReservation(Long memberId, Long concertId, String orderName,
                                                OffsetDateTime expiresAt, int totalPrice, int amount) {

        return new Reservation(memberId, concertId, orderName, expiresAt, totalPrice, amount);

    }


    public void confirmReservation() {
        status = ReservationStatus.CONFIRMED;
        this.expiresAt = OffsetDateTime.now().plusMinutes(RESERVATION_TIMEOUT_MINUTES);
    }

    public void cancelReservation() {
        status = ReservationStatus.CANCELLED;
    }


    public void updateOrderId(String orderId) {
        this.orderId = orderId;
    }
}
