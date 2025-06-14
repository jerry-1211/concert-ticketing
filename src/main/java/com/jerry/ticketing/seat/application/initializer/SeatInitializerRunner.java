package com.jerry.ticketing.seat.application.initializer;

import com.jerry.ticketing.seat.application.manager.SeatManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SeatInitializerRunner implements CommandLineRunner {
    private final SeatManager seatManager;

    @Override
    public void run(String... args) {
        seatManager.initializeSeats();
    }

}
