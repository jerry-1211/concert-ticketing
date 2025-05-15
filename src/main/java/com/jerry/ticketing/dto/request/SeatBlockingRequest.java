package com.jerry.ticketing.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SeatBlockingRequest {

    private Long concertId;
    private List<Long> seatIds;
    private Long memberId;

}
