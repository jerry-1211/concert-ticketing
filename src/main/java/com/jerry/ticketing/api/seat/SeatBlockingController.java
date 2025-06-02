package com.jerry.ticketing.api.seat;


import com.jerry.ticketing.application.seat.SeatBlockingService;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.dto.BlockingSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatBlockingController {
    private final SeatBlockingService seatBlockingService;

    /**
     * 좌석 선점
     * */
    @PostMapping("/blocks")
    public ResponseEntity<BlockingSeat.Response> blockSeats(@RequestBody BlockingSeat.Request request) {

        return ResponseEntity.ok(seatBlockingService.blockSeats(request));

    }
}
