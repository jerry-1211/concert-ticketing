package com.jerry.ticketing.seat.infrastructure.scheduler;


import com.jerry.ticketing.seat.application.ConcertSeatCommandService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertSeatScheduler {
    private final ConcertSeatCommandService seatBlockingService;

    @Scheduled(fixedRate = ConcertSeat.BLOCKING_CHECK_INTERVAL_SECONDS)
    public void releaseExpiredBlockedSeats() {
        seatBlockingService.releaseExpiredBlockedSeats();
    }
}
