package com.jerry.ticketing.test;

import com.jerry.ticketing.seat.application.ConcertSeatCommandService;
import com.jerry.ticketing.seat.application.dto.web.BlockConcertSeatDto;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile("test")
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class TestConcertSeatApiController {
    private final ConcertSeatCommandService seatBlockingService;

    @PostMapping("/blocks")
    public ResponseEntity<BlockConcertSeatDto.Response> blockSeats(@RequestBody BlockConcertSeatDto.Request request) {

        List<ConcertSeat> blockedConcertSeats = seatBlockingService.blockSeats(request);

        return ResponseEntity.ok(BlockConcertSeatDto.Response.from(blockedConcertSeats));

    }
}
