package com.jerry.ticketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatBlockingResponse {

    private List<Long> blockedSeatIds;

    private OffsetDateTime expireAt;
}
