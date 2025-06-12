package com.jerry.ticketing.concert.application;

import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public List<ConcertDto> findAllConcerts() {
        return concertRepository.findAll().stream().map(ConcertDto::from).toList();
    }


    @Transactional(readOnly = true)
    public ConcertDto findConcertById(Long concertId) {
        return ConcertDto.from(concertRepository.findById(concertId).orElseThrow());
    }

}
