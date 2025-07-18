package com.jerry.ticketing.seat.infrastructure.repository.section;

import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.domain.port.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class TSectionRepositoryAdapterTest {

    @Autowired
    private SectionJpaRepository jpaRepository;

    private SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        sectionRepository = new SectionRepositoryAdapter(jpaRepository);
    }


    @Test
    @DisplayName("구역(section)을 모두 저장 할 수 있다.")
    void saveAllSections() {
        // given
        Section section1 = Section.of(1L, "A", 100);
        Section section2 = Section.of(1L, "B", 100);
        Section section3 = Section.of(1L, "C", 100);

        // when
        List<Section> sections = sectionRepository.saveAll(List.of(section1, section2, section3));

        // then
        assertThat(sections).hasSize(3)
                .extracting("concertId", "zone", "capacity")
                .containsExactlyInAnyOrder(
                        tuple(1L, "A", 100),
                        tuple(1L, "B", 100),
                        tuple(1L, "C", 100)
                );
    }


    @Test
    @DisplayName("id로 구역(section)을 찾을 수 있다.")
    void findSectionById() {
        // given
        Section section = Section.of(1L, "A", 100);
        Section savedSection = sectionRepository.save(section);

        // when
        Optional<Section> foundSection = sectionRepository.findById(savedSection.getId());

        // then
        assertThat(foundSection).isPresent()
                .hasValueSatisfying(
                        s -> {
                            assertThat(s).extracting("concertId", "zone", "capacity")
                                    .containsExactlyInAnyOrder(1L, "A", 100);
                        }
                );
    }


    @Test
    @DisplayName("id들로 구역(section)들을 찾을 수 있다.")
    void findAllSectionById() {
        // given
        Section section1 = Section.of(1L, "A", 100);
        Section section2 = Section.of(1L, "B", 100);
        Section savedSection1 = sectionRepository.save(section1);
        Section savedSection2 = sectionRepository.save(section2);

        // when
        List<Section> sections = sectionRepository.findAllById(List.of(savedSection1.getId(), savedSection2.getId()));

        // then
        assertThat(sections).hasSize(2)
                .extracting("concertId", "zone", "capacity")
                .containsExactlyInAnyOrder(
                        tuple(1L, "A", 100),
                        tuple(1L, "B", 100)
                );
    }


    @Test
    @DisplayName("콘서트 id로 구역(section)이 존재 하는지 알 수 있다.")
    void existsSectionByConcertId() {
        // given
        long concertId = 1L;
        Section section = Section.of(concertId, "A", 100);
        sectionRepository.save(section);

        // when // then
        assertThat(sectionRepository.existsByConcertId(concertId)).isTrue();
    }


    @Test
    @DisplayName("콘서트 id로 구역(section)이 존재 하는지 않는 것을 알 수 있다.")
    void notExistsSectionByConcertIdAndZone() {
        // given
        long concertId = 1L;

        // when // then
        assertThat(sectionRepository.notExistsByConcertId(concertId)).isTrue();

    }


    @Test
    @DisplayName("콘서트 id로 구역(section)이 있는지 확인한다.")
    void findSectionByConcertIdAndZone() {
        // given
        long concertId = 1L;
        String zone = "A";
        Section section1 = Section.of(concertId, zone, 100);
        Section section2 = Section.of(concertId, "B", 100);

        sectionRepository.save(section1);
        sectionRepository.save(section2);

        // when
        Optional<Section> foundSection = sectionRepository.findByConcertIdAndZone(concertId, zone);

        // then
        assertThat(foundSection).isPresent()
                .hasValueSatisfying(
                        s -> {
                            assertThat(s).extracting("concertId", "zone", "capacity")
                                    .containsExactlyInAnyOrder(concertId, zone, 100);
                        }
                );
    }


}