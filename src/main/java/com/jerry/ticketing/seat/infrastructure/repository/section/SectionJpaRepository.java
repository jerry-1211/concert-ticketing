package com.jerry.ticketing.seat.infrastructure.repository.section;

import com.jerry.ticketing.seat.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionJpaRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByConcertIdAndZone(Long concertId, String zone);

    boolean existsByConcertId(Long concertId);
}
