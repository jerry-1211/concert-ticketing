package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ConcertErrorCode;
import com.jerry.ticketing.global.exception.SeatErrorCode;
import com.jerry.ticketing.global.exception.SectionErrorCode;
import com.jerry.ticketing.seat.domain.enums.SectionType;
import com.jerry.ticketing.seat.infrastructure.batch.BatchSaveHelper;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ConcertSeatManager {
    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final SeatRepository seatRepository;

    private final BatchSaveHelper batchSaveHelper;

    /**
     * 콘서트 좌석 43,000석 생성 로직
     */
    public void createIfNotExists(Long concertId) {
        if (concertSeatRepository.findByConcertId(concertId).isEmpty()) {
            createAllConcertSeats(concertId);
        }
    }

    private void createAllConcertSeats(Long concertId) {
        AtomicLong seatId = new AtomicLong(1L);
        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() -> new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND));


        SectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> {
                            createConcertSeats(concert, type, String.valueOf((char) zone), seatId);
                        })
        );

    }


    private void createConcertSeats(Concert concert, SectionType type, String zone, AtomicLong seatId) {
        List<ConcertSeat> concertSeatBatch = new ArrayList<>();

        int totalCreated = 0;
        Section section = sectionRepository.findByConcertIdAndZone(concert.getId(), zone)
                .orElseThrow(() -> new BusinessException(SectionErrorCode.SECTION_NOT_FOUND));

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {

                Seat seat = seatRepository.findById(seatId.getAndIncrement())
                        .orElseThrow(() -> new BusinessException(SeatErrorCode.SEAT_NOT_FOUND));

                ConcertSeat concertSeat = ConcertSeat.of(concert.getId(), seat.getId(), section.getId(), type.concertSeatAmount(concert.getPrice()));
                concertSeatBatch.add(concertSeat);

                totalCreated = batchSaveHelper.saveIfFull(concertSeatBatch, totalCreated, concertSeatRepository);
            }
        }

        batchSaveHelper.saveRemaining(concertSeatBatch, totalCreated, concertSeatRepository);
    }

}
