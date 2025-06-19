package com.jerry.ticketing.member.application;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.MemberErrorCode;
import com.jerry.ticketing.member.application.dto.MyPageDto;
import com.jerry.ticketing.member.application.dto.ReservationListDto;
import com.jerry.ticketing.member.application.dto.UpdateProfile;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final MemberRepository memberRepository;
    private final ConcertQueryService concertQueryService;
    private final ReservationQueryService reservationQueryService;


    public MyPageDto getMyPage(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MyPageDto.from(member);
    }

    public MyPageDto updateMyPage(String email, UpdateProfile.Request request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateProfile(request);

        return MyPageDto.from(member);
    }

    public List<ReservationListDto> getMyReservation(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<Reservation> reservations = reservationQueryService.getReservation(member.getId());
        List<ReservationListDto> reservationDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Concert concert = concertQueryService.getConcertById(reservation.getConcertId(), Function.identity());

            ReservationListDto reservationListDto = ReservationListDto.from(reservation, concert);

            reservationDtos.add(reservationListDto);
        }

        return reservationDtos;
    }


}
