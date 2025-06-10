package com.jerry.ticketing.seat.infrastructure.repository;

import com.jerry.ticketing.seat.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section,Long> {
    Optional<Section> findByZone(String zone);

    Optional<Section> findByConcertId(Long ConcertId);

    Optional<Section> findByConcertIdAndZone(Long concertId, String zone);

    boolean existsByConcertId(Long concertId);
}
