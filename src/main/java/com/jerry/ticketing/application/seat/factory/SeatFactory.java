package com.jerry.ticketing.application.seat.factory;


import com.jerry.ticketing.application.seat.enums.SectionType;
import com.jerry.ticketing.application.seat.util.BatchSaveHelper;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.repository.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SeatFactory {

    // 좌석 생성 배치 크기
    private final SeatRepository seatRepository;
    private final BatchSaveHelper batchSaveHelper;

    /**
     * 초기 좌석(VO객체)을 생성합니다.
     * */
    public void initializeSeats(){
        if (seatRepository.count() == 0) {
            createAllSeats();
        }
    }

    private void createAllSeats() {
        log.info("좌석 초기화를 시작합니다...");
        SectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone(), type.getEndZone())
                        .forEach(zone -> createSeats(type))
        );
        log.info("좌석 데이터 초기화가 완료되었습니다.");
    }


    /**
     * Section Config를 기반으로 실제 Seat 객체를 생성합니다.
     * */
    private void createSeats(SectionType type) {
        List<Seat> seatBatch = new ArrayList<>();
        int totalCreated = 0;

        for (char rowChar = type.getStartRow(); rowChar <= type.getEndRow(); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = Seat.createSeat(rowChar, number, type.getSeatType());
                seatBatch.add(seat);

                totalCreated = batchSaveHelper.saveIfFull(seatBatch, totalCreated, seatRepository);
            }
        }

        totalCreated = batchSaveHelper.saveRemaining(seatBatch, totalCreated, seatRepository);
        log.debug("총 {}개 좌석 생성 완료", totalCreated);
    }

}

