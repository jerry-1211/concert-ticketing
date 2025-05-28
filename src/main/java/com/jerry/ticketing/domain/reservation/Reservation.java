package com.jerry.ticketing.domain.reservation;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.member.Member;
import com.jerry.ticketing.domain.reservation.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private ReservationStatus reservationStatus;

    //예약 만들어진 시점
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    //예약 만료 기한
    @Column(nullable = false)
    private LocalDate expiresAt;
}
