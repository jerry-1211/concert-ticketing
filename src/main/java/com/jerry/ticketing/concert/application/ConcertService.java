package com.jerry.ticketing.concert.application;

import com.jerry.ticketing.seat.application.ConcertInitializationService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.domain.ConcertMapper;
import com.jerry.ticketing.concert.application.dto.ConcertList;
import com.jerry.ticketing.concert.application.dto.CreateConcert;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<ConcertList.Response> getAllConcerts(){
        return  concertRepository.findAll().stream().map(ConcertList.Response::from).toList();
    }



}
