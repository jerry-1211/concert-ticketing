package com.jerry.ticketing.seat.application.concertseat.web;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BlockConcertSeatDto {
    @NoArgsConstructor
    @Getter
    public static class Request {

        private Long concertId;
        private List<Long> seatIds;
        private Long memberId;

        public Request(Long concertId, List<Long> seatIds, Long memberId) {
            this.concertId = concertId;
            this.seatIds = seatIds;
            this.memberId = memberId;
        }
    }


    @Getter
    @Builder
    public static class Response {

        private List<Long> blockedSeatIds;
        private OffsetDateTime expireAt;
        private int totalAmount;


        public static BlockConcertSeatDto.Response from(List<ConcertSeat> blockedConcertSeats) {
            return BlockConcertSeatDto.Response.builder()
                    .blockedSeatIds(blockedConcertSeats.stream().map(ConcertSeat::getId).collect(Collectors.toList()))
                    .expireAt(blockedConcertSeats.get(0).getExpireAt())
                    .totalAmount(ConcertSeats.calculateTotalAmount(ConcertSeats.from(blockedConcertSeats)))
                    .build();
        }
    }


}
