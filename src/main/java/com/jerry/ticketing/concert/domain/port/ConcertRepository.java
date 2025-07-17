package com.jerry.ticketing.concert.domain.port;

import com.jerry.ticketing.concert.domain.Concert;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {

    Concert save(Concert concert);

    List<Concert> findAll();

    Optional<Concert> findById(Long id);
}

