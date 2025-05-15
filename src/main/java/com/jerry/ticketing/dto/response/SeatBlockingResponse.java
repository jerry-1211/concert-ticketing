package com.jerry.ticketing.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatBlockingResponse {
    private List<Long> blockedSeatIds;
    private LocalDateTime expireAt;

}
