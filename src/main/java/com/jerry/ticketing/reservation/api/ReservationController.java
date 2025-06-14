package com.jerry.ticketing.reservation.api;


import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationCommandService reservationCommandService;

    @PostMapping
    public ResponseEntity<CreateReservationDto.Response> createReservation(
            @Valid @RequestBody CreateReservationDto.Request request) {
        return ResponseEntity.ok(reservationCommandService.create(request));

    }


}
