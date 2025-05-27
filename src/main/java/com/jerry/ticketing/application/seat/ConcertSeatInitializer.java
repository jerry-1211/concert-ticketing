package com.jerry.ticketing.application.seat;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import com.jerry.ticketing.repository.seat.SeatRepository;
import com.jerry.ticketing.repository.seat.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConcertSeatInitializer {

    private final SeatRepository seatRepository;
    private final SectionRepository sectionRepository;
    private final ConcertRepository concertRepository;
    private final ConcertSeatRepository concertSeatRepository;

     // 좌석 생성 배치 크기
     private static final int BATCH_SIZE = 1000;


    /**
     * 구역과 콘서트 좌석 데이터를 생성합니다.
     * */
    public void initializeSectionAndConcertSeats(Long concertId){

        if (sectionRepository.existsByConcertId(concertId)) {
            log.info("콘서트 좌석 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }
        log.info("콘서트 좌석과 구역을 초기화를 시작합니다...");
        initializeAllSectionAndConcertSeats(concertId);
        log.info("콘서트 좌석과 구역 데이터 초기화가 완료되었습니다.");

    }


    /**
     * Section과 ConcertSeat 모든 초기화 데이터 생성합니다.
     * */
    protected void initializeAllSectionAndConcertSeats(Long concertId) {
        Section section = createSectionIfNotExists(concertId);
        if (concertSeatRepository.findByConcertId(concertId).isEmpty()){
            createConcertSeats(concertId, section);
        }
    }


    /**
     * Section A-Z 까지 만듭니다.
     * @param concertId 콘서트 아이디
     * */
    private Section createSectionIfNotExists(Long concertId) {
        return sectionRepository.findByConcertId(concertId)
                .orElseGet(() -> {
                    List<Section> createdSections = new ArrayList<>();
                    List<SectionConfig> configs = getSectionConfigs();

                    Concert concert = concertRepository.findById(concertId)
                            .orElseThrow(() -> new RuntimeException("Concert not found"));

                    for (SectionConfig config : configs) {
                        for (char zone = config.getStartZone(); zone <= config.getEndZone(); zone++) {
                            Section section = Section.initSection(concert,String.valueOf(zone), config.getCapacity());
                            createdSections.add(sectionRepository.save(section));
                        }
                    }
                    return createdSections.isEmpty() ? Collections.emptyList() : createdSections.get(0);
                });
    }


    /**
     * ConcertSeat을 Section, Concert, Seat에 매핑
     * @param section 콘서트 아이디
     */
    private void createConcertSeats(Long concertId, Section section) {
        List<ConcertSeat> concertSeatBatch = new ArrayList<>(BATCH_SIZE);
        long currentCreatedCount = 1L;
        int totalCreated = 0;

        List<SectionConfig> configs = getSectionConfigs();
        Concert concert = concertRepository.findById(concertId).orElse(null);

        for (SectionConfig config : configs) {
            for (char zone = config.getStartZone(); zone <= config.getEndZone(); zone++) {
                for (char rowChar = config.getStartRow(); rowChar <= config.getEndRow(); rowChar++) {
                    for (int number = config.getStartNumber(); number <= config.getEndNumber(); number++) {
                        Seat seat = seatRepository.findById(currentCreatedCount++).orElse(null);

                        ConcertSeat concertSeat = ConcertSeat.builder()
                                .concert(concert)
                                .seat(seat)
                                .section(section)
                                .price((concert != null ? concert.getPrice() : 0) * config.getPremium())
                                .status(ConcertSeatStatus.AVAILABLE)
                                .build();

                        concertSeatBatch.add(concertSeat);
                        if (concertSeatBatch.size() >= BATCH_SIZE){
                            concertSeatRepository.saveAll(concertSeatBatch);
                            totalCreated += concertSeatBatch.size();
                            concertSeatBatch.clear();
                            log.debug("{}개 콘서트 좌석 저장 완료", totalCreated);
                        }

                    }
                }
                if(!concertSeatBatch.isEmpty()){
                    concertSeatRepository.saveAll(concertSeatBatch);
                    totalCreated += concertSeatBatch.size();
                }
            }
        }

        log.debug("총 {}개 콘서트 좌석 생성 완료", totalCreated);
    }

    private static List<SectionConfig> getSectionConfigs() {
        return List.of(
                SectionConfig.vipSection(), SectionConfig.standardSection(), SectionConfig.economySection()
        );
    }

}
