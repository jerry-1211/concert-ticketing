package com.jerry.ticketing.concert.api;

import com.jerry.ticketing.concert.application.ConcertService;
import com.jerry.ticketing.concert.application.dto.CreateConcertDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertApiController {


    private final ConcertService concertService;

    /**
     * 새로운 콘서스 생성
     * */
    @PostMapping
    public ResponseEntity<CreateConcertDto.Response> createConcert(
            @Valid @RequestBody CreateConcertDto.Request request){
        CreateConcertDto.Response response = concertService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
