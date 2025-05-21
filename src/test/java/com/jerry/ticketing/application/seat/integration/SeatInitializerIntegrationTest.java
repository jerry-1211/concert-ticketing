package com.jerry.ticketing.application.seat.integration;

import com.jerry.ticketing.application.seat.SeatInitializer;
import com.jerry.ticketing.application.seat.SectionConfig;
import com.jerry.ticketing.repository.seat.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SeatInitializerIntegrationTest {

    @Autowired
    private SeatInitializer seatInitializer;

    @Autowired
    private SeatRepository seatRepository;


    @BeforeEach
    void setUp(){
        seatRepository.deleteAll();
    }

    @Test
    @DisplayName("좌석 초기화 후 올바른 개수의 좌성이 생성되었는지 확인")
    void shouldCreateCorrectNumberOfSeats(){
        // Given
        assertThat(seatRepository.count()).isEqualTo(0L);

        // When
        seatInitializer.initializeSeats();

        // Then
        // A-F까지 VIP 구역 설정 (a-e열, 1-100 좌석)
        int seatFromAtoF = SectionConfig.calculateSeats(SectionConfig.vipSection()) * 6;
        int seatFromGtoL = SectionConfig.calculateSeats(SectionConfig.standardSection()) * 6;
        int seatFromMtoZ = SectionConfig.calculateSeats(SectionConfig.economySection()) * 14;
        int seatTotal = seatFromAtoF + seatFromGtoL + seatFromMtoZ;

        assertThat(seatRepository.count()).isEqualTo(seatTotal);
    }
}
