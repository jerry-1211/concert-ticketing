package com.jerry.ticketing.dto;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BlockingSeat {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Request {

        private Long concertId;

        private List<Long> seatIds;

        private Long memberId;

    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private List<Long> blockedSeatIds;

        private OffsetDateTime expireAt;

        public static BlockingSeat.Response toResponse(List<ConcertSeat> blockedConcertSeats){
            return new BlockingSeat.Response(
                    blockedConcertSeats.stream().map(ConcertSeat::getId).collect(Collectors.toList()),
                    blockedConcertSeats.get(0).getBlockedExpireAt()
            );
        }
    }



}
