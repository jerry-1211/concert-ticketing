package com.jerry.ticketing.api.concert;

import com.jerry.ticketing.application.concert.ConcertService;
import com.jerry.ticketing.dto.CreateConcert;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertController {


    private final ConcertService concertService;

    /**
     * 새로운 콘서스 생성
     * */
    @PostMapping
    public ResponseEntity<CreateConcert.Response> createConcert(
            @Valid @RequestBody CreateConcert.Request request){
        CreateConcert.Response response = concertService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
