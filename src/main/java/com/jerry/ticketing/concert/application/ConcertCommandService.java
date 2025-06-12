package com.jerry.ticketing.concert.application;

import com.jerry.ticketing.seat.application.initializer.ConcertInitializationService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.infrastructure.mapper.ConcertMapper;
import com.jerry.ticketing.concert.application.dto.web.CreateConcertDto;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertCommandService {

    private final ConcertRepository concertRepository;
    private final ConcertInitializationService concertSeatInitializer;
    private final ConcertMapper concertMapper;

    @Transactional
    public CreateConcertDto.Response createConcert(CreateConcertDto.Request request) {

        Concert concert = concertMapper.buildConcert(request);
        Concert saveConcert = concertRepository.save(concert);

        // 좌석 & 섹션 초기화
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert.getId());

        return CreateConcertDto.Response.from(saveConcert);

    }


}
