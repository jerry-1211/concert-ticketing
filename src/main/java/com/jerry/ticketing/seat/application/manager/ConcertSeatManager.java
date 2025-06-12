package com.jerry.ticketing.seat.application.manager;


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
import lombok.extern.slf4j.Slf4j;
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
     * ConcertSeat을 Section, Concert, Seat에 매핑
     *
     * @param
     */
    public void createIfNotExists(Long concertId) {
        if (concertSeatRepository.findByConcertId(concertId).isEmpty()) {
            createAllConcertSeats(concertId);
        }
    }

    private void createAllConcertSeats(Long concertId) {
        AtomicLong seatId = new AtomicLong(1L);
        Concert concert = concertRepository.findById(concertId).orElse(null);
        SectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> {
                            assert concert != null;
                            createConcertSeats(concert, type, String.valueOf((char) zone), seatId);
                        })
        );

    }


    private void createConcertSeats(Concert concert, SectionType type, String zone, AtomicLong seatId) {
        List<ConcertSeat> concertSeatBatch = new ArrayList<>();
        int totalCreated = 0;

        Section section = sectionRepository.findByConcertIdAndZone(concert.getId(), zone).orElse(null);

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = seatRepository.findById(seatId.getAndIncrement()).orElse(null);
                int price = concert.getPrice() * type.getPremium();

                ConcertSeat concertSeat = ConcertSeat.creatConcertSeat(concert.getId(), seat.getId(), section.getId(), price);
                concertSeatBatch.add(concertSeat);

                totalCreated = batchSaveHelper.saveIfFull(concertSeatBatch, totalCreated, concertSeatRepository);
            }
        }

        totalCreated = batchSaveHelper.saveRemaining(concertSeatBatch, totalCreated, concertSeatRepository);
    }

}
