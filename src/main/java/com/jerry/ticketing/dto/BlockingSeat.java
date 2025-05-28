package com.jerry.ticketing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

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
    }



}
