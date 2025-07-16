package com.jerry.ticketing.seat.api;


import com.jerry.ticketing.seat.application.ConcertSeatCommandService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.application.dto.web.BlockConcertSeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Profile("!test")
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class ConcertSeatApiController {
    private final ConcertSeatCommandService seatBlockingService;

    @PostMapping("/blocks")
    public ResponseEntity<BlockConcertSeatDto.Response> blockSeats(@RequestBody BlockConcertSeatDto.Request request, Authentication authentication) {

        List<ConcertSeat> blockedConcertSeats = seatBlockingService.occupy(request);

        return ResponseEntity.ok(BlockConcertSeatDto.Response.from(blockedConcertSeats));

    }
}
