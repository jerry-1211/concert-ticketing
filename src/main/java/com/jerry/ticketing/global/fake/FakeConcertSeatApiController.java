package com.jerry.ticketing.global.fake;

import com.jerry.ticketing.seat.application.concertseat.ConcertSeatCommandService;
import com.jerry.ticketing.seat.application.concertseat.web.BlockConcertSeatDto;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Profile("test")
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class FakeConcertSeatApiController {
    private final ConcertSeatCommandService seatBlockingService;

    @PostMapping("/blocks")
    public ResponseEntity<BlockConcertSeatDto.Response> blockSeats(@RequestBody BlockConcertSeatDto.Request request) {

        List<ConcertSeat> blockedConcertSeats = seatBlockingService.occupy(request);
        return ResponseEntity.ok(BlockConcertSeatDto.Response.from(blockedConcertSeats));
    }


}
