package com.jerry.ticketing.seat.api;


import com.jerry.ticketing.seat.application.SeatBlockingService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.application.dto.BlockingSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class ConcertSeatApiController {
    private final SeatBlockingService seatBlockingService;

    /**
     * 좌석 선점
     * */
    @PostMapping("/blocks")
    public ResponseEntity<BlockingSeat.Response> blockSeats(@RequestBody BlockingSeat.Request request) {

        List<ConcertSeat> blockedConcertSeats = seatBlockingService.blockSeats(request);

        return ResponseEntity.ok(BlockingSeat.Response.toResponse(blockedConcertSeats));

    }
}
