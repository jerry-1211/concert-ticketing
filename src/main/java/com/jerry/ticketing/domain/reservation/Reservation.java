package com.jerry.ticketing.domain.reservation;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.*;

import java.time.OffsetDateTime;
import org.springframework.util.CollectionUtils;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    // 예약 id
    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

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
    private ReservationStatus reservationStatus;

    //예약 만들어진 시점
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    //예약 만료 기한
    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    // 결제 티켓 숫자
    @Column(nullable = false)
    private int amount;

    private Reservation(Long memberId, Concert concert, int totalPrice, ReservationStatus reservationStatus, OffsetDateTime createdAt, OffsetDateTime expiresAt, int amount) {
        this.memberId = memberId;
        this.concert = concert;
        this.totalPrice = totalPrice;
        this.reservationStatus = reservationStatus;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.amount = amount;
    }

    public static Reservation createReservation (Long memberId, Concert concert){

        return new Reservation(memberId, concert, 1000, ReservationStatus.PENDING, OffsetDateTime.now(), OffsetDateTime.now(),3);

    }

    public void confirmReservation(){
        reservationStatus = ReservationStatus.CONFIRMED;
    }

    public void addReservationItem(ReservationItem reservationItem){

        reservationItems.add(reservationItem);

    }

    public void owner(Member member) {
        if(!Objects.equals(this.memberId, member.getId())) {
             throw new IllegalArgumentException("너꺼아니야");
        }
    }
}
