package com.jerry.ticketing.api.concert;

import com.jerry.ticketing.application.concert.ConcertService;
import com.jerry.ticketing.dto.request.ConcertCreateRequest;
import com.jerry.ticketing.dto.response.ConcertResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concert")
@RequiredArgsConstructor
public class ConcertController {


    private final ConcertService concertService;

    /**
     * 새로운 콘서스 생성
     * */
    @PostMapping
    public ResponseEntity<ConcertResponse> createConcert(
            @Valid @RequestBody ConcertCreateRequest request){
        ConcertResponse createdConcert = concertService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdConcert);

    }


}
