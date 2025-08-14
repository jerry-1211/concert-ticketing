package com.jerry.ticketing.reservation.domain;


import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    public static final int RESERVATION_TIMEOUT = 2;
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
    private int totalAmount;

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
    private int quantity;

    // 주문 Id
    @Column
    private String orderId;

    // 주문자 명
    @Column
    private String orderName;


    private Reservation(Long memberId, Long concertId, String orderName, OffsetDateTime createdAt, OffsetDateTime expiresAt, int totalAmount, int quantity) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.orderName = orderName;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.status = ReservationStatus.PENDING;
    }


    public static Reservation of(Long memberId, Long concertId, String orderName, OffsetDateTime createdAt,
                                 OffsetDateTime expiresAt, int totalAmount, int quantity) {
        return new Reservation(memberId, concertId, orderName, createdAt, expiresAt, totalAmount, quantity);

    }

    public void confirmReservation() {
        status = ReservationStatus.CONFIRMED;
        updateExpiresAt();
    }

    public void cancelReservation() {
        status = ReservationStatus.CANCELLED;
    }

    public void updateOrderId(String orderId) {
        this.orderId = orderId;
    }

    private void updateExpiresAt() {
        this.expiresAt = expiresAt.plusMinutes(RESERVATION_TIMEOUT);
    }

}
