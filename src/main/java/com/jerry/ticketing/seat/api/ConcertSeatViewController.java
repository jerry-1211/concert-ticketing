package com.jerry.ticketing.seat.api;


import com.jerry.ticketing.seat.application.ConcertSeatService;
import com.jerry.ticketing.seat.domain.enums.SectionType;
import com.jerry.ticketing.seat.application.dto.ConcertSeatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
public class ConcertSeatViewController {

    private final ConcertSeatService concertSeatService;


    @GetMapping("/concert-seats")
    public String seatReservationPage(@RequestParam Long concertId, Model model) {
        model.addAttribute("concertId", concertId);
        return "seat-reservation";
    }

    @GetMapping("/api/zones")
    public ResponseEntity<List<String>> getZone(@RequestParam Long concertId) {
        return ResponseEntity.ok(SectionType.createZoneList());
    }


    @GetMapping("/api/rows")
    public ResponseEntity<List<String>> getRows(@RequestParam String zone) {
        return ResponseEntity.ok(SectionType.getRowsByZone(zone));
    }


    @GetMapping("/api/seats")
    public ResponseEntity<List<ConcertSeatDto.Response>> getSeats(@RequestParam Long concertId,
                                                                  @RequestParam String zone,
                                                                  @RequestParam String row) {

        return ResponseEntity.ok(concertSeatService.getSeats(concertId, zone, row));
    }


}
