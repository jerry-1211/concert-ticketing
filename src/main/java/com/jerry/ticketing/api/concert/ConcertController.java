package com.jerry.ticketing.api.concert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.ticketing.application.concert.ConcertService;
import com.jerry.ticketing.dto.ConcertCreate;
import com.jerry.ticketing.dto.request.ConcertCreateRequest;
import com.jerry.ticketing.dto.response.ConcertResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

// API 표준 복수형 작성
// RestApi 복수형 . concerts


@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
public class ConcertController {


    private final ConcertService concertService;

    /**
     * 새로운 콘서스 생성
     * */

    @PostMapping
    public ResponseEntity<ConcertCreate.Response> createConcert(
            @Valid @RequestBody ConcertCreate.Request request){

        ConcertCreate.Response createdConcert = concertService.createConcert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdConcert);
    }
}
