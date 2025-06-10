package com.jerry.ticketing.scheduler;


import com.jerry.ticketing.reservation.application.ReservationService;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationService reservationService;

    @Scheduled(fixedRate = Reservation.PENDING_CHECK_INTERVAL_SECONDS)
    public void releaseExpiredReservation() {
        log.info("기한 지난 결제 스케줄러 실행");
        reservationService.releaseExpiredReservation();
    }
}
