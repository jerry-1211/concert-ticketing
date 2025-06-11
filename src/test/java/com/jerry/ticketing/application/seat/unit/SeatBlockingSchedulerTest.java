package com.jerry.ticketing.application.seat.unit;

import com.jerry.ticketing.seat.infrastructure.scheduler.ConcertSeatScheduler;
import com.jerry.ticketing.seat.application.ConcertSeatBlockingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SeatBlockingSchedulerTest {

    @Mock
    private ConcertSeatBlockingService seatBlockingService;

    @InjectMocks
    private ConcertSeatScheduler seatBlockingScheduler;

    @Test
    void releaseExpiredBlockedSeats_ShouldCallService() {
        // When
        seatBlockingScheduler.releaseExpiredBlockedSeats();

        // Then
        verify(seatBlockingService, times(1)).releaseExpiredBlockedSeats();
    }
}