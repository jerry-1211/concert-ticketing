package com.jerry.ticketing.config.runner;

import com.jerry.ticketing.application.seat.SeatInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class SeatInitializerRunner implements CommandLineRunner {
    private final SeatInitializer seatInitializer;

    @Override
    public void run(String ...args){
            seatInitializer.initializeSeats();
    }

}
