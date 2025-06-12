package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.seat.domain.enums.SectionType;
import com.jerry.ticketing.seat.infrastructure.batch.BatchSaveHelper;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class SeatManager {

    // 좌석 생성 배치 크기
    private final SeatRepository seatRepository;
    private final BatchSaveHelper batchSaveHelper;

    /**
     * 초기 좌석(VO객체)을 생성합니다.
     */
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


    /**
     * Section Config를 기반으로 실제 Seat 객체를 생성합니다.
     */
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

