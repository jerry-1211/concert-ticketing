package com.jerry.ticketing.application.seat.integration;

import com.jerry.ticketing.application.seat.ConcertSeatInitializer;
import com.jerry.ticketing.application.seat.SeatInitializer;
import com.jerry.ticketing.global.config.section.SectionConfig;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.seat.Section;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import com.jerry.ticketing.repository.seat.SeatRepository;
import com.jerry.ticketing.repository.seat.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ConcertSeatCreationIntegrationTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatInitializer seatInitializer;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertSeatInitializer concertSeatInitializer;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;


    @BeforeEach
    void setUp(){
        concertSeatRepository.deleteAll();
        sectionRepository.deleteAll();
        concertRepository.deleteAll();
        seatRepository.deleteAll();
    }


    @Test
    @DisplayName("올바른 수의 구역과 좌석을 생성 테스트")
    void shouldCreateCorrectNumberOfSectionsAndSeats(){

        // Given
        seatInitializer.initializeSeats();

        Concert concert = Concert.builder()
                .title("Cold Play")
                .dateTime(OffsetDateTime.now())
                .venue("일산 고양시 대화동")
                .price(100_000)
                .description("Cold Play의 2번째 내한 공연")
                .maxTicketsPerUser(2)
                .build();

        Concert saveConcert = concertRepository.save(concert);

        // When
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert.getId());

        // Then
        // 총 26개의 구역(A-Z) 생성 확인
        assertThat(sectionRepository.count()).isEqualTo(26);

        // 각 주요 구역이 ConcertId와 잘 매핑 되었는지 확인
        Section sectionA = sectionRepository.findByZone("A").orElseThrow();
        Section sectionG = sectionRepository.findByZone("G").orElseThrow();
        Section sectionM = sectionRepository.findByZone("M").orElseThrow();

        assertThat(sectionA.getConcert().getId()).isEqualTo(saveConcert.getId());
        assertThat(sectionG.getConcert().getId()).isEqualTo(saveConcert.getId());
        assertThat(sectionM.getConcert().getId()).isEqualTo(saveConcert.getId());


        /*
         * 전체 좌석수 확인
         * VIP(A-F): 까지 6구역
         * STANDARD(G-L): 까지 6구역
         * ECONOMY(M-Z): 까지 14구역
         * 총합: 43,000 좌석
         * */

        // 총 43,000개의 (VO) 좌석 생성 & 콘서트 좌석 확인
        assertThat(seatRepository.count()).isEqualTo(calculateTotalSeats());
        assertThat(concertSeatRepository.count()).isEqualTo(calculateTotalSeats());
    }


    @Test
    @DisplayName("여러 콘서트에 대해 각각 올바른 수의 구역과 좌석 생성 테스트")
    void shouldCreatCorrectSectionsAndSeatsForMultipleConcerts() {
        // Given
        seatInitializer.initializeSeats();

        Concert concert1 = Concert.builder()
                .title("Cold Play")
                .dateTime(OffsetDateTime.now())
                .venue("일산 고양시 대화동")
                .price(100_000)
                .description("Cold Play의 2번째 내한 공연")
                .maxTicketsPerUser(2)
                .build();

        Concert concert2 = Concert.builder()
                .title("아이유")
                .dateTime(OffsetDateTime.now())
                .venue("일산 고양시 마두동")
                .price(200_000)
                .description("아이유의 신곡 발표 콘서트")
                .maxTicketsPerUser(3)
                .build();

        Concert saveConcert1 = concertRepository.save(concert1);
        Concert saveConcert2 = concertRepository.save(concert2);

        // When
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert1.getId());
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert2.getId());

        // Then
        // Seat(VO) 좌석은 43,000 석 생성
        assertThat(seatRepository.count()).isEqualTo(calculateTotalSeats());


        /*
        * ConcertSeat 좌석은 콘서트별 생성
        * 현재 테스트 코드의 콘서트는 2개이므로 총 86,000석 생성
        * */
        assertThat(concertSeatRepository.count()).isEqualTo(calculateTotalSeats() * 2L);

    }



    private static int calculateTotalSeats() {
        int totalVipSeats = SectionConfig.vipSection().getCapacity() * 6;
        int totalStandardSeats = SectionConfig.standardSection().getCapacity() * 6;
        int totalEconomySeats = SectionConfig.economySection().getCapacity() * 14;
        return totalVipSeats + totalStandardSeats + totalEconomySeats;
    }


}
