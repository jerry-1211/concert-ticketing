package com.jerry.ticketing.seat.application.initializer;

import com.jerry.ticketing.seat.application.manager.SeatManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class SeatInitializerRunner implements CommandLineRunner {
    private final SeatManager seatManager;

    @Override
    public void run(String... args) {
        seatManager.initializeSeats();
    }

}
