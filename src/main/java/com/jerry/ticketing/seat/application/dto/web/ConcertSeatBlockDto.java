package com.jerry.ticketing.seat.application.dto.web;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ConcertSeatBlockDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {

        private Long concertId;
        private List<Long> concertSeatIds;
        private Long memberId;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private List<Long> blockedSeatIds;
        private OffsetDateTime expireAt;
        private int totalAmount;


        public static ConcertSeatBlockDto.Response toResponse(List<ConcertSeat> blockedConcertSeats) {
            return new ConcertSeatBlockDto.Response(
                    blockedConcertSeats.stream().map(ConcertSeat::getId).collect(Collectors.toList()),
                    blockedConcertSeats.get(0).getBlockedExpireAt(),
                    ConcertSeat.calculateTotalPrice(blockedConcertSeats)
            );
        }
    }


}
