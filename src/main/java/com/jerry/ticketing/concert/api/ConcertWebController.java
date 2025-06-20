package com.jerry.ticketing.concert.api;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.concert.application.dto.web.CreateConcertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertWebController {

    private final ConcertQueryService concertQueryService;

    @GetMapping
    public String showAllConcerts(Model model) {
        List<ConcertDto> concerts = concertQueryService.getAllConcerts(ConcertDto::from);
        model.addAttribute("concerts", concerts);
        return "concert/concert-list";
    }


    @GetMapping("/new")
    public String creatConcertForm(Model model) {
        model.addAttribute("createConcertRequest", new CreateConcertDto.Request());
        return "concert/concert-new";
    }

}

