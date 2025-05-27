package com.jerry.ticketing.application.concert;

import com.jerry.ticketing.application.seat.ConcertSeatInitializer;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.concert.ConcertMapper;
import com.jerry.ticketing.dto.ConcertCreate;
import com.jerry.ticketing.dto.request.ConcertCreateRequest;
import com.jerry.ticketing.dto.response.ConcertResponse;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ConcertSeatInitializer concertSeatInitializer;
    private final ConcertMapper concertMapper;

    @Transactional
    public ConcertCreate.Response createConcert(ConcertCreate.Request request) {

        Concert concert = concertMapper.buildConcert(request);

        Concert saveConcert = concertRepository.save(concert);

        // 좌석 & 섹션 초기화
        concertSeatInitializer.initializeSectionAndConcertSeats(saveConcert.getId());

        return ConcertCreate.toResponse(saveConcert);
    }
}
