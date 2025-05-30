package com.jerry.ticketing.domain.seat;

import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.repository.seat.SeatRepository;
import com.jerry.ticketing.repository.seat.SectionRepository;
import com.jerry.ticketing.domain.TestFixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SeatTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private SeatRepository seatRepository;


    @BeforeEach
    void setUp(){

    }

    @Test
    @DisplayName("예약 좌석 생성 및 저장 검증")
    void saveSeat(){
        // Given
        Concert concert = TestFixture.createConcert();
        Section section = TestFixture.createSection(concert);
        Seat seat = TestFixture.createSeat();

        //When
        Seat saveSeat = seatRepository.save(seat);

        // Then
        assertThat(saveSeat).isNotNull();
        assertThat(saveSeat.getId()).isNotNull();
        assertThat(saveSeat.getSeatRow()).isEqualTo('A');
        assertThat(saveSeat.getNumber()).isEqualTo(10);

    }
}