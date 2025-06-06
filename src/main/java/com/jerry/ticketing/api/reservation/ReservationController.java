package com.jerry.ticketing.api.reservation;


import com.jerry.ticketing.application.reservation.ReservationService;
import com.jerry.ticketing.dto.CreateReservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<CreateReservation.Response> createReservation(
            @Valid @RequestBody CreateReservation.Request request) {
        return ResponseEntity.ok(reservationService.createReservation(request)) ;

    }


}
