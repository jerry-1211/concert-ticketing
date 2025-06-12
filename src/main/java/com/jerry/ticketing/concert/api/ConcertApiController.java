package com.jerry.ticketing.concert.api;

import com.jerry.ticketing.concert.application.ConcertCommandService;
import com.jerry.ticketing.concert.application.dto.web.CreateConcertDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertApiController {


    private final ConcertCommandService concertCommandService;

    /**
     * 새로운 콘서스 생성
     */
    @PostMapping
    public ResponseEntity<CreateConcertDto.Response> createConcert(
            @Valid @RequestBody CreateConcertDto.Request request) {
        CreateConcertDto.Response response = concertCommandService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
