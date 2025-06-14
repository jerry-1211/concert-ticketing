package com.jerry.ticketing.reservation.infrastructure.scheduler;


import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationCommandService reservationCommandService;

    @Scheduled(fixedRate = Reservation.PENDING_CHECK_INTERVAL_SECONDS)
    public void releaseExpiredReservation() {
        reservationCommandService.releaseExpiredReservations();
    }
}
