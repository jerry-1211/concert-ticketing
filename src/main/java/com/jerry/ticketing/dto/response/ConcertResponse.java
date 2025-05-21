package com.jerry.ticketing.dto.response;

import com.jerry.ticketing.domain.concert.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ConcertResponse {

    private Long id;
    private String title;
    private LocalDateTime dateTime;
    private String venue;
    private int price;
    private String description;
    private int maxTicketsPerUser;

    public static ConcertResponse from (Concert concert){
        return ConcertResponse.builder()
                .id(concert.getId())
                .title(concert.getTitle())
                .dateTime(concert.getDateTime())
                .venue(concert.getVenue())
                .price(concert.getPrice())
                .description(concert.getDescription())
                .maxTicketsPerUser(concert.getMaxTicketsPerUser())
                .build();
    }
}
