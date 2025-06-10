package com.jerry.ticketing.concert.application.dto;

import com.jerry.ticketing.concert.domain.Concert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

public class ConcertDto {


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

        public static ConcertDto.Response from (Concert concert){
            return ConcertDto.Response.builder()
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
