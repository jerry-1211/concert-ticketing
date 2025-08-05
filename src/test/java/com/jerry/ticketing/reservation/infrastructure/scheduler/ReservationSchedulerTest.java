package com.jerry.ticketing.reservation.infrastructure.scheduler;

import com.jerry.ticketing.reservation.application.ReservationCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationSchedulerTest {

    @Mock
    private ReservationCommandService reservationCommandService;

    @InjectMocks
    private ReservationScheduler reservationScheduler;


    @Test
    @DisplayName("예약 서비스에서 만료된 예약들을 없애는 메서드를 호출한다.")
    void callReleaseExpiredReservations() {
        // given // when
        reservationScheduler.releaseExpiredReservation();

        // then
        verify(reservationCommandService, times(1)).releaseExpiredReservations();
    }
}