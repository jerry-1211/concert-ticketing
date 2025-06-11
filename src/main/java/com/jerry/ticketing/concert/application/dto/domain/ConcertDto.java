package com.jerry.ticketing.concert.application.dto.domain;

import com.jerry.ticketing.concert.domain.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;


@Getter
@Builder
@AllArgsConstructor

public class ConcertDto {

    private Long concertId;
    private String title;
    private OffsetDateTime dateTime;
    private String venue;
    private int price;
    private String description;
    private int maxTicketsPerUser;

    public static ConcertDto from(Concert concert) {
        return ConcertDto.builder()
                .concertId(concert.getId())
                .title(concert.getTitle())
                .dateTime(concert.getDateTime())
                .venue(concert.getVenue())
                .price(concert.getPrice())
                .description(concert.getDescription())
                .maxTicketsPerUser(concert.getMaxTicketsPerUser())
                .build();
    }
}
