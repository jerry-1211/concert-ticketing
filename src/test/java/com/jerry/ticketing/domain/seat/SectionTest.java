package com.jerry.ticketing.domain.seat;

import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.repository.seat.SectionRepository;
import com.jerry.ticketing.domain.TestFixture;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class SectionTest {

    @Autowired
    private SectionRepository sectionRepository;

    @Test
    @DisplayName("구역 생성 및 저장 검증")
    void saveSection(){
        //Given
        Concert concert = TestFixture.createConcert();
        Section section = TestFixture.createSection(concert);

        // When
        Section saveSection = sectionRepository.save(section);

        // Then
        assertThat(saveSection).isNotNull();
        assertThat(saveSection.getId()).isNotNull();
        assertThat(saveSection.getZone()).isEqualTo("A");
        assertThat(saveSection.getCapacity()).isEqualTo(100_000);
        assertThat(saveSection.getConcert().getTitle()).isEqualTo("Cold Play");
    }
}