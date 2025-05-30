package com.jerry.ticketing.application.concert;

import com.jerry.ticketing.application.seat.ConcertInitializationService;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.concert.ConcertMapper;
import com.jerry.ticketing.dto.CreateConcert;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertInitializationService concertSeatInitializer;
    private final ConcertMapper concertMapper;

    @Transactional
    public CreateConcert.Response createConcert(CreateConcert.Request request){

        Concert concert = concertMapper.buildConcert(request);

        Concert saveConcert = concertRepository.save(concert);

        // 좌석 & 섹션 초기화
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert.getId());

        return CreateConcert.Response.from(saveConcert);

    }
}
