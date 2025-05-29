package com.jerry.ticketing.domain.concert;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)

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
    private OffsetDateTime dateTime;

    // 콘서트 장소
    @Column(nullable = false)
    private String venue;

    // 콘서트 가격
    @Column(nullable = false)
    private int price;

    // 콘서트 설명
    private String description;

    // 콘서트 최대 티켓 예약 가능 숫자
    @Column(nullable = false)
    private int maxTicketsPerUser = 3;

    private Concert(String title, OffsetDateTime dateTime, String venue, int price, String description, int maxTicketsPerUser) {
        this.title = title;
        this.dateTime = dateTime;
        this.venue = venue;
        this.price = price;
        this.description = description;
        this.maxTicketsPerUser = maxTicketsPerUser;
    }

    public static Concert createConcert(String title, OffsetDateTime dateTime,
                                        String venue, int price, String description, int maxTicketsPerUser) {
        return new Concert(title, dateTime, venue, price, description,maxTicketsPerUser);
    }

}
