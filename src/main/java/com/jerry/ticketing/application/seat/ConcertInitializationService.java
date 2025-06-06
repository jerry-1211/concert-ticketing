package com.jerry.ticketing.application.seat;


import com.jerry.ticketing.application.seat.factory.ConcertSeatFactory;
import com.jerry.ticketing.application.seat.factory.SectionFactory;
import com.jerry.ticketing.repository.seat.SectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcertInitializationService {

    private final SectionRepository sectionRepository;
    private final SectionFactory sectionFactory;
    private final ConcertSeatFactory concertSeatFactory;


    /**
     * 구역과 콘서트 좌석 데이터를 생성합니다.
     * */
    @Transactional
    public void initializeSectionAndConcertSeats(Long concertId){

        if (sectionRepository.existsByConcertId(concertId)) {
            log.info("콘서트 좌석 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("콘서트 좌석과 구역을 초기화를 시작합니다...");
        sectionFactory.createIfNotExists(concertId);


        concertSeatFactory.createIfNotExists(concertId);
        log.info("콘서트 좌석과 구역 데이터 초기화가 완료되었습니다.");

    }


}
