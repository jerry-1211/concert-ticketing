package com.jerry.ticketing.dto.response;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatBlockingResponse {

    private List<Long> blockedSeatIds;

    private LocalDateTime expireAt;

    public static SeatBlockingResponse toResponse(List<ConcertSeat> blockedSeat) {

        return new SeatBlockingResponse(
                blockedSeat.stream().map(ConcertSeat::getId).collect(Collectors.toList()),
                blockedSeat.get(0).getBlockedExpireAt());
    }
}
