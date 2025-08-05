package com.jerry.ticketing.reservation.domain.vo;

import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Reservations {
    private final List<Reservation> reservations;

    public List<Reservation> item() {
        return this.reservations;
    }

    public static Reservations from(List<Reservation> reservations) {
        return new Reservations(reservations);
    }

    public void cancel() {
        this.reservations.forEach(Reservation::cancelReservation);
    }

}
