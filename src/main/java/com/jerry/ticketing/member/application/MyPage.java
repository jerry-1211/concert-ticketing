package com.jerry.ticketing.member.application;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.application.dto.MyPageDto;
import com.jerry.ticketing.member.application.dto.MyReservationListDto;
import com.jerry.ticketing.member.application.dto.UpdateProfile;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MyPage {

    private final MemberQueryService memberQueryService;
    private final ReservationQueryService reservationQueryService;
    private final ConcertQueryService concertQueryService;


    public MyPageDto getMyPage(String email) {
        return MyPageDto.from(memberQueryService.getMemberByEmail(email, Function.identity()));
    }


    public List<MyReservationListDto> getMyReservation(String email) {
        Member member = memberQueryService.getMemberByEmail(email, Function.identity());
        List<Reservation> reservations = reservationQueryService.getReservation(member.getId());
        return getMyReservationListDto(reservations);
    }


    public MyPageDto updateMyPage(String email, UpdateProfile request) {
        return MyPageDto.from(memberQueryService.updateProfile(email, request));
    }


    private List<MyReservationListDto> getMyReservationListDto(List<Reservation> reservations) {
        List<MyReservationListDto> myReservations = new ArrayList<>();
        addMyReservation(reservations, myReservations);
        return myReservations;
    }

    private void addMyReservation(List<Reservation> reservations, List<MyReservationListDto> myReservations) {
        for (Reservation reservation : reservations) {
            Concert concert = concertQueryService.getConcertById(reservation.getConcertId(), Function.identity());
            MyReservationListDto myReservationListDto = MyReservationListDto.from(reservation, concert);
            myReservations.add(myReservationListDto);
        }
    }

}
