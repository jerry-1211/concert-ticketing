package com.jerry.ticketing.global.fake;


import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Profile("test")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservation")
public class FakeReservationController {

    private final ReservationRepository reservationRepository;

    @PostMapping
    public ResponseEntity<CreateReservationDto.Response> createReservation(
            @Valid @RequestBody CreateReservationDto.Request request) {

        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(
                1L, request.getConcertId(), request.getOrderName(), dateTime, request.getExpireAt(), request.getTotalAmount(), request.getQuantity());

        Reservation savedReservation = reservationRepository.save(reservation);

        return ResponseEntity.ok(CreateReservationDto.Response.from(savedReservation));
    }

}
