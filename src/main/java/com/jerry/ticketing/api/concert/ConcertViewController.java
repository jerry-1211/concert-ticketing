package com.jerry.ticketing.api.concert;


import com.jerry.ticketing.application.concert.ConcertService;
import com.jerry.ticketing.dto.ConcertList;
import com.jerry.ticketing.dto.CreateConcert;
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

