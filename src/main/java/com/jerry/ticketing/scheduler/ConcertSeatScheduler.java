package com.jerry.ticketing.scheduler;


import com.jerry.ticketing.seat.application.ConcertSeatBlockingService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConcertSeatScheduler {
    private final ConcertSeatBlockingService seatBlockingService;

    @Scheduled(fixedRate = ConcertSeat.BLOCKING_CHECK_INTERVAL_SECONDS)
    public void releaseExpiredBlockedSeats() {
        log.info("만료된 좌석 선점 해제 스케줄러 실행");
        seatBlockingService.releaseExpiredBlockedSeats();
    }
}
