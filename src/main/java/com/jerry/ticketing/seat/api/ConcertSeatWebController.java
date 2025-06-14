package com.jerry.ticketing.seat.api;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.seat.application.ConcertSeatQueryService;
import com.jerry.ticketing.seat.application.dto.web.DetailedConcertSeatDto;
import com.jerry.ticketing.seat.domain.enums.SeatSectionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
public class ConcertSeatWebController {

    private final ConcertQueryService concertQueryService;
    private final ConcertSeatQueryService concertSeatQueryService;


    @GetMapping("/concert-seats")
    public String seatReservationPage(@RequestParam Long concertId, Model model) {

        ConcertDto concert = concertQueryService.getConcertById(concertId, ConcertDto::from);
        model.addAttribute("concert", concert);
        return "seat-reservation";
    }


    @GetMapping("/api/zones")
    public ResponseEntity<List<String>> getZone(@RequestParam Long concertId) {
        return ResponseEntity.ok(SeatSectionType.getZones());
    }


    @GetMapping("/api/rows")
    public ResponseEntity<List<String>> getRows(@RequestParam String zone) {
        return ResponseEntity.ok(SeatSectionType.getRows(zone));
    }


    @GetMapping("/api/seats")
    public ResponseEntity<List<DetailedConcertSeatDto>> getSeats(@RequestParam Long concertId,
                                                                 @RequestParam String zone,
                                                                 @RequestParam String row) {

        return ResponseEntity.ok(concertSeatQueryService.getDetailedConcertSeat(concertId, zone, row));
    }


}
