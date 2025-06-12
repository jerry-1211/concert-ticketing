package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.seat.domain.enums.SectionType;
import com.jerry.ticketing.seat.infrastructure.batch.BatchSaveHelper;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class SeatManager {

    private final SeatRepository seatRepository;
    private final BatchSaveHelper batchSaveHelper;

    public void initializeSeats() {
        if (seatRepository.count() == 0) {
            createAllSeats();
        }
    }

    private void createAllSeats() {
        SectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> createSeats(type))
        );
    }


    private void createSeats(SectionType type) {
        List<Seat> seatBatch = new ArrayList<>();
        int totalCreated = 0;

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = Seat.createSeat(String.valueOf(rowChar), number, type.getSeatType());
                seatBatch.add(seat);

                totalCreated = batchSaveHelper.saveIfFull(seatBatch, totalCreated, seatRepository);
            }
        }

        totalCreated = batchSaveHelper.saveRemaining(seatBatch, totalCreated, seatRepository);
    }

}

