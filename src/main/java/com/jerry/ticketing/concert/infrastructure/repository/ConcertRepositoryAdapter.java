package com.jerry.ticketing.concert.infrastructure.repository;


import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.domain.port.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryAdapter implements ConcertRepository {

    private final ConcertJpaRepository jpaRepository;

    @Override
    public Concert save(Concert concert) {
        return jpaRepository.save(concert);
    }

    @Override
    public List<Concert> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Concert> findById(Long id) {
        return jpaRepository.findById(id);
    }

}
