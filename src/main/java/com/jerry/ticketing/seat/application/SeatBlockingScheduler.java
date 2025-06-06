package com.jerry.ticketing.seat.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatBlockingScheduler {
    private final SeatBlockingService seatBlockingService;

    @Scheduled(fixedRate = 30000)
    public void releaseExpiredBlockedSeats(){
        log.info("만료된 좌석 선점 해제 스케줄러 실행");
        seatBlockingService.releaseExpiredBlockedSeats();
    }
}
