package com.jerry.ticketing.application.seat.factory;


import com.jerry.ticketing.application.seat.enums.SectionType;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertSeatFactory {
    private static final int BATCH_SIZE = 1000;
    
    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final SeatRepository seatRepository;

    /**
     * ConcertSeat을 Section, Concert, Seat에 매핑
     * @param
     */
    public void createIfNotExists(Long concertId) {
        if(concertSeatRepository.findByConcertId(concertId).isEmpty()){
            Concert concert = concertRepository.findById(concertId).orElse(null);
            createConcertSeats(concert);
        }
    }
    
    
    private void createConcertSeats(Concert concert){
        List<ConcertSeat> concertSeatBatch = new ArrayList<>(BATCH_SIZE);
        long seatId = 1L;
        int totalCreated = 0;


        for (SectionType type : SectionType.getSectionTypes()) {
            for (char zone = type.getStartZone(); zone <= type.getEndZone(); zone++) {
                Section section = sectionRepository.findByConcertIdAndZone(concert.getId(),zone).orElse(null);
                
                for (char rowChar = type.getStartRow(); rowChar <= type.getEndRow(); rowChar++) {
                    for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                        Seat seat = seatRepository.findById(seatId++).orElse(null);
                        int price = concert.getPrice() * type.getPremium();

                        ConcertSeat concertSeat = ConcertSeat.creatConcertSeat(concert, seat, section, price);
                        concertSeatBatch.add(concertSeat);
                        
                        totalCreated = saveBatchIfFull(concertSeatBatch, totalCreated);
                    }
                }
            }
        }

        totalCreated = saveRemainingBatch(concertSeatBatch, totalCreated);
        log.debug("{}개 콘서트 좌석 저장 완료", totalCreated);
    }
    

    private int saveRemainingBatch(List<ConcertSeat> concertSeatBatch, int totalCreated) {
        if(!concertSeatBatch.isEmpty()){
            concertSeatRepository.saveAll(concertSeatBatch);
            totalCreated += concertSeatBatch.size();
        }
        return totalCreated;
    }

    private int saveBatchIfFull(List<ConcertSeat> concertSeatBatch, int totalCreated) {
        if (concertSeatBatch.size() >= BATCH_SIZE){
            concertSeatRepository.saveAll(concertSeatBatch);
            totalCreated += concertSeatBatch.size();
            concertSeatBatch.clear();
        }
        return totalCreated;
    }
    
}
