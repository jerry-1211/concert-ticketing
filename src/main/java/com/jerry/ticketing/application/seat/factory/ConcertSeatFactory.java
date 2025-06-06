package com.jerry.ticketing.application.seat.factory;


import com.jerry.ticketing.application.seat.enums.SectionType;
import com.jerry.ticketing.application.seat.util.BatchSaveHelper;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import com.jerry.ticketing.repository.seat.SeatRepository;
import com.jerry.ticketing.repository.seat.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertSeatFactory {
    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final SeatRepository seatRepository;

    private final BatchSaveHelper batchSaveHelper;

    /**
     * ConcertSeat을 Section, Concert, Seat에 매핑
     * @param
     */
    public void createIfNotExists(Long concertId) {
        if(concertSeatRepository.findByConcertId(concertId).isEmpty()){
            createAllConcertSeats(concertId);
        }
    }

    private void createAllConcertSeats(Long concertId){
        AtomicLong seatId = new AtomicLong(1L);
        Concert concert = concertRepository.findById(concertId).orElse(null);
        SectionType.getSectionTypes().forEach(type ->
                IntStream.rangeClosed(type.getStartZone().charAt(0), type.getEndZone().charAt(0))
                        .forEach(zone -> {
                            assert concert != null;
                            createConcertSeats(concert,type, String.valueOf((char)zone),seatId);
                        })
        );

        log.info("{}콘서트 좌석 데이터가 생성 완료", concert != null ? concert.getTitle() : null);
    }


    private void createConcertSeats(Concert concert, SectionType type, String zone, AtomicLong seatId){
        List<ConcertSeat> concertSeatBatch = new ArrayList<>();
        int totalCreated = 0;

        Section section = sectionRepository.findByConcertIdAndZone(concert.getId(),zone).orElse(null);

        for (char rowChar = type.getStartRow().charAt(0); rowChar <= type.getEndRow().charAt(0); rowChar++) {
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = seatRepository.findById(seatId.getAndIncrement()).orElse(null);
                int price = concert.getPrice() * type.getPremium();

                ConcertSeat concertSeat = ConcertSeat.creatConcertSeat(concert, seat, section, price);
                concertSeatBatch.add(concertSeat);

                totalCreated = batchSaveHelper.saveIfFull(concertSeatBatch, totalCreated,concertSeatRepository);
            }
        }

        totalCreated = batchSaveHelper.saveRemaining(concertSeatBatch, totalCreated,concertSeatRepository);
        log.debug("{}개 콘서트 좌석 저장 완료", totalCreated);
    }

}
