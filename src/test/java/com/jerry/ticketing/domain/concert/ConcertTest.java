package com.jerry.ticketing.domain.concert;

import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.domain.TestFixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ConcertTest {

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    void setUp() {
        concertRepository.deleteAll();
    }

    @Test
    @DisplayName("콘서트 생성 및 저장 검증")
    void saveConcert(){
        // Given
        Concert concert = TestFixture.createConcert();

        // When
        Concert saveConcert = concertRepository.save(concert);

        // Then
        assertThat(saveConcert).isNotNull();
        assertThat(saveConcert.getId()).isNotNull();
        assertThat(saveConcert.getTitle()).isEqualTo("Test-Title");
        assertThat(saveConcert.getPrice()).isEqualTo(100_000);
        assertThat(saveConcert.getMaxTicketsPerUser()).isEqualTo(3);
        assertThat(saveConcert.getDateTime()).isEqualTo(OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES));

    }
}
