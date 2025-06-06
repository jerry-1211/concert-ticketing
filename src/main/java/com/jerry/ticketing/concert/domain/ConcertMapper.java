package com.jerry.ticketing.concert.domain;

import com.jerry.ticketing.concert.application.dto.CreateConcert;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {

    public Concert buildConcert(CreateConcert.Request request) {
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
