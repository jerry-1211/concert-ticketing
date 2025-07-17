package com.jerry.ticketing.seat.application.manager;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.seat.application.concertseat.ConcertSeatCommandService;
import com.jerry.ticketing.seat.application.concertseat.ConcertSeatQueryService;
import com.jerry.ticketing.seat.application.seat.SeatQueryService;
import com.jerry.ticketing.seat.application.section.SectionQueryService;
import com.jerry.ticketing.seat.domain.enums.SeatSectionType;
import com.jerry.ticketing.seat.infrastructure.batch.BatchHelper;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ConcertSeatManager {

    private final ConcertSeatQueryService concertSeatQueryService;
    private final ConcertQueryService concertQueryService;
    private final SeatQueryService seatQueryService;
    private final SectionQueryService sectionQueryService;

    private final BatchHelper batchHelper;
    private final ConcertSeatCommandService concertSeatCommandService;


    /**
     * 콘서트 좌석 43,000석 생성 로직
     */
    public void createIfNotExists(Long concertId) {
        if (concertSeatQueryService.hasNoConcertSeats(concertId)) {
            createAll(concertId);
        }
    }


    private void createAll(Long concertId) {
        AtomicLong seatId = new AtomicLong(1L);

        Concert concert = concertQueryService.getConcertById(concertId, Function.identity());

        SeatSectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> {
                            create(concert, type, String.valueOf((char) zone), seatId);
                        })
        );
    }


    private void create(Concert concert, SeatSectionType type, String zone, AtomicLong seatId) {
        List<ConcertSeat> concertSeatBatch = new ArrayList<>();

        Section section = sectionQueryService.getSection(concert.getId(), zone);

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {

                Seat seat = seatQueryService.getSeat(seatId.getAndIncrement());

                ConcertSeat concertSeat = ConcertSeat.of(concert.getId(), seat.getId(), section.getId(), type.concertSeatAmount(concert.getPrice()));
                concertSeatBatch.add(concertSeat);

                if (batchHelper.isFull(concertSeatBatch)) {
                    concertSeatCommandService.saveAll(concertSeatBatch);
                    concertSeatBatch.clear();
                }

            }
        }

        if (batchHelper.hasRemaining(concertSeatBatch)) {
            concertSeatCommandService.saveAll(concertSeatBatch);
        }
    }

}
