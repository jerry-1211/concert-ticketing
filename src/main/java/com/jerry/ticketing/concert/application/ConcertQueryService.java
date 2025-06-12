package com.jerry.ticketing.concert.application;

import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public <T> List<T> findAllConcerts(Function<Concert, T> mapper) {
        return concertRepository.findAll().stream().map(mapper).toList();
    }


    @Transactional(readOnly = true)
    public <T> T findConcertById(Long concertId, Function<Concert, T> mapper) {
        Concert concert = concertRepository.findById(concertId).orElseThrow();
        return mapper.apply(concert);
    }

}
