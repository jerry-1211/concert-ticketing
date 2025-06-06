package com.jerry.ticketing.concert.api;


import com.jerry.ticketing.concert.application.ConcertService;
import com.jerry.ticketing.concert.application.dto.ConcertList;
import com.jerry.ticketing.concert.application.dto.CreateConcert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/concerts")
@RequiredArgsConstructor
public class ConcertViewController {

    private final ConcertService concertService;


    @GetMapping
    public String showAllConcerts(Model model){
        List<ConcertList.Response> concerts = concertService.getAllConcerts();
        model.addAttribute("concerts", concerts);
        return "concert-list";
    }


    @GetMapping("/new")
    public String creatConcertForm(Model model){
        model.addAttribute("createConcertRequest", new CreateConcert.Request());
        return "create-concert";
    }

}

