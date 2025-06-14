package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.seat.application.SeatCommandService;
import com.jerry.ticketing.seat.application.SeatQueryService;
import com.jerry.ticketing.seat.domain.enums.SeatSectionType;
import com.jerry.ticketing.seat.infrastructure.batch.BatchHelper;
import com.jerry.ticketing.seat.domain.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class SeatManager {


    private final SeatCommandService seatCommandService;
    private final SeatQueryService seatQueryService;
    private final BatchHelper batchHelper;

    public void initializeSeats() {
        if (seatQueryService.hasNoSeats()) {
            createAll();
        }
    }

    private void createAll() {
        SeatSectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> create(type))
        );
    }


    private void create(SeatSectionType type) {
        List<Seat> seatBatch = new ArrayList<>();

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = Seat.of(String.valueOf(rowChar), number, type.getSeatType());
                seatBatch.add(seat);

                if (batchHelper.isFull(seatBatch)) {
                    seatCommandService.saveAll(seatBatch);
                    seatBatch.clear();
                }

            }
        }

        if (batchHelper.hasRemaining(seatBatch)) {
            seatCommandService.saveAll(seatBatch);
        }
    }

}

