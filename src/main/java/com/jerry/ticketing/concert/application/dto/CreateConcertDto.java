package com.jerry.ticketing.concert.application.dto;

import com.jerry.ticketing.concert.domain.Concert;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class CreateConcertDto {


    @NoArgsConstructor
    @Getter
    public static class Request {
        @NotBlank(message = "콘서트 제목은 필수입니다.")
        @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
        private String title;

        @NotNull(message = "공연 날짜는 필수입니다.")
        @FutureOrPresent(message = "공연 날짜가 현재 또는 미래여야 합니다.")
        private OffsetDateTime dateTime;

        @NotBlank(message = "공연 장소는 필수입니다.")
        @Size(max = 100, message = "공연 장소는 100자를 초과할 수 없습니다.")
        private String venue;

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0원 이상이여야 합니다.")
        @Max(value = 100_000_000, message = "가격은 1억원을 초과할 수 없습니다.")
        private int price;

        @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
        private String description;

        @NotNull(message = "사용자당 최대 티켓 수는 필수입니다.")
        @Min(value = 1, message = "사용자당 최대 티켓 수는 1개 이상이여야 합니다.")
        @Max(value = 5, message = "사용자당 최대 티켓 수는 5개 이하이여야 합니다.")
        private int maxTicketsPerUser;

        public Request(String title, OffsetDateTime dateTime, String venue, int price, String description, int maxTicketsPerUser) {
            this.title = title;
            this.dateTime = dateTime;
            this.venue = venue;
            this.price = price;
            this.description = description;
            this.maxTicketsPerUser = maxTicketsPerUser;
        }

        public static CreateConcertDto.Request of(String title, OffsetDateTime dateTime,
                                                  String venue, int price, String description, int maxTicketsPerUser) {

            return new CreateConcertDto.Request(title, dateTime, venue, price, description, maxTicketsPerUser);

        }
    }


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

        public static CreateConcertDto.Response from (Concert concert){
            return CreateConcertDto.Response.builder()
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
