package com.jerry.ticketing.domain.seat;

import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import com.jerry.ticketing.domain.TestFixture;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.Section;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ConcertSeatTest {

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private SeatRepository seatRepository;

    private Concert saveConcert;
    private Section saveSection;
    private Seat saveSeat;

    @BeforeEach
    void setUp(){
        Concert concert = TestFixture.createConcert();
        Section section = TestFixture.createSection(concert);
        Seat seat = TestFixture.createSeat();

        this.saveSection = sectionRepository.save(section);
        this.saveConcert = concertRepository.save(concert);
        this.saveSeat =seatRepository.save(seat);
    }

    @Test
    @DisplayName("콘서트 예약 생성 및 저장 검증")
    void saveSeat(){
        // Given
        ConcertSeat concertSeat = TestFixture.createConcertSeat(saveConcert, saveSeat, saveSection);
        concertSeatRepository.save(concertSeat);

        //When
        List<ConcertSeat> concertSeats = concertSeatRepository.findBySeatId(saveSeat.getId());

        // Then
        assertThat(concertSeats).hasSize(1);
        assertThat(concertSeats.get(0).getSeat().getSeatRow()).isEqualTo("A");

    }


}