package com.jerry.ticketing.domain.concert;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Concert {

    // 콘서트 id
    @Id
    @Column(name = "concert_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 콘서트 명
    @Column(nullable = false)
    private String title;

    // 콘서트 예약 날짜
    @Column(nullable = false)
    private LocalDate date;

    // 콘서트 장소
    @Column(nullable = false)
    private String venue;

    // 콘서트 설명
    private String description;

    // 콘서트 최대 티켓 예약 가능 숫자
    @Column(nullable = false)
    @Builder.Default
    private int maxTicketsPerUser = 3;

}
