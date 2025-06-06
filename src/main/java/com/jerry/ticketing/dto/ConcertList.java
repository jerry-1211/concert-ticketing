package com.jerry.ticketing.dto;

import com.jerry.ticketing.domain.concert.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

public class ConcertList {


    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        private Long id;
        private String title;
        private OffsetDateTime dateTime;
        private String venue;
        private int price;
        private String description;
        private int maxTicketsPerUser;

        public static ConcertList.Response from (Concert concert){
            return ConcertList.Response.builder()
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
}
