package com.jerry.ticketing.concert.domain;

import com.jerry.ticketing.concert.application.dto.CreateConcertDto;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {

    public Concert buildConcert(CreateConcertDto.Request request) {
        return Concert.createConcert(
                request.getTitle(),
                request.getDateTime(),
                request.getVenue(),
                request.getPrice(),
                request.getDescription(),
                request.getMaxTicketsPerUser()
        );
    }

}
