package com.jerry.ticketing.api.seat;


import com.jerry.ticketing.application.seat.ConcertSeatService;
import com.jerry.ticketing.application.seat.enums.SectionType;
import com.jerry.ticketing.dto.ConcertSeatSelect;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatReservationController {

    private final ConcertSeatService concertSeatService;


    @GetMapping("/concert-seats")
    public String seatReservationPage(@RequestParam Long concertId, Model model){
        model.addAttribute("concertId", concertId);
        return "concert-seats";
    }

    @GetMapping("/api/zones")
    public ResponseEntity<List<String>> getZone(@RequestParam Long concertId){
        return ResponseEntity.ok(SectionType.createZoneList());
    }


    @GetMapping("/api/rows")
    public ResponseEntity<List<String>> getRows(@RequestParam String zone){
        return ResponseEntity.ok(SectionType.getRowsByZone(zone));
    }


    @GetMapping("/api/seats")
    public ResponseEntity<List<ConcertSeatSelect.Response>> getSeats(@RequestParam Long concertId,
                                                                     @RequestParam String zone,
                                                                     @RequestParam String row){

        return ResponseEntity.ok(concertSeatService.getSeats(concertId, zone, row));
    }



}
