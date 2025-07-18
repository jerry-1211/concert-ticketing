package com.jerry.ticketing.seat.infrastructure.repository.section;

import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.seat.domain.port.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryAdapter implements SectionRepository {

    private final SectionJpaRepository jpaRepository;

    @Override
    public Section save(Section section) {
        return jpaRepository.save(section);
    }

    @Override
    public List<Section> saveAll(List<Section> sections) {
        return jpaRepository.saveAll(sections);
    }

    @Override
    public Optional<Section> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Section> findAllById(List<Long> ids) {
        return jpaRepository.findAllById(ids);
    }

    @Override
    public boolean existsByConcertId(Long concertId) {
        return jpaRepository.existsByConcertId(concertId);
    }

    @Override
    public boolean notExistsByConcertId(Long concertId) {
        return !existsByConcertId(concertId);
    }

    @Override
    public Optional<Section> findByConcertIdAndZone(Long concertId, String zone) {
        return jpaRepository.findByConcertIdAndZone(concertId, zone);
    }


}
