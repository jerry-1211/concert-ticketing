package com.jerry.ticketing.domain.reservation;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    // 예약 id
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 멤버 id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    Member member;

    // 콘서트 id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

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

    private Reservation(Member member, Concert concert, int totalPrice, ReservationStatus status, OffsetDateTime createdAt, OffsetDateTime expiresAt, int amount) {
        this.member = member;
        this.concert = concert;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.amount = amount;
    }

    public Reservation(Member member, Concert concert, String orderName, OffsetDateTime expiresAt, int totalPrice, int amount) {
        this.member = member;
        this.concert = concert;
        this.orderName = orderName;
        this.expiresAt = expiresAt;
        this.totalPrice = totalPrice;
        this.amount = amount;
        this.status = ReservationStatus.PENDING;
        this.createdAt = OffsetDateTime.now();
    }

    public static Reservation createReservation (Member member, Concert concert,
                                                 int totalPrice, ReservationStatus status,
                                                 OffsetDateTime createdAt, OffsetDateTime expiresAt, int amount){

        return new Reservation(member, concert, totalPrice, status, createdAt, expiresAt,amount);

    }


    public static Reservation createReservation(Member member, Concert concert, String orderName,
                                                OffsetDateTime expiresAt, int totalPrice, int amount) {

        return new Reservation(member, concert, orderName, expiresAt, totalPrice, amount);

    }


    public void confirmReservation(){
        status = ReservationStatus.CONFIRMED;
        this.expiresAt = OffsetDateTime.now().plusMonths(3);
    }

    public void updateOrderId(String orderId){
        this.orderId = orderId;
    }
}
