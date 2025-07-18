package com.jerry.ticketing.seat.domain.port;

import com.jerry.ticketing.seat.domain.Section;

import java.util.List;
import java.util.Optional;

public interface SectionRepository {

    Optional<Section> findByConcertIdAndZone(Long concertId, String zone);

    boolean existsByConcertId(Long concertId);

    boolean notExistsByConcertId(Long concertId);

    List<Section> saveAll(List<Section> sections);

    Section save(Section section);

    List<Section> findAllById(List<Long> ids);

    Optional<Section> findById(Long id);


}
