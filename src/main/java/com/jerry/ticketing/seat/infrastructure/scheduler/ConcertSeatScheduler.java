package com.jerry.ticketing.seat.infrastructure.scheduler;


import com.jerry.ticketing.seat.application.concertseat.ConcertSeatCommandService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertSeatScheduler {
    private final ConcertSeatCommandService ConcertSeatCommandService;

    @Scheduled(fixedRate = ConcertSeat.BLOCKING_CHECK_INTERVAL_SECONDS)
    public void releaseExpiredConcertSeats() {
        ConcertSeatCommandService.releaseExpiredConcertSeats();
    }
}
