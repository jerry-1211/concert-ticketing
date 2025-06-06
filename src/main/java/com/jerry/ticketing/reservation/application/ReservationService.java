package com.jerry.ticketing.reservation.application;


import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.member.domain.Member;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.application.dto.CreateReservationDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.member.infrastructure.repository.MemberRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ConcertRepository concertRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void confirmReservation(String orderId) {
        Reservation reservation = reservationRepository.findByOrderId(orderId)
                .orElseThrow(()->new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        reservation.confirmReservation();
    }


    @Transactional
    public CreateReservationDto.Response  createReservation(CreateReservationDto.Request request){
        // 나중에 MemberService 로 옮기기
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException());

        // 나중에 ConcertService 로 옮기기
        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(() -> new RuntimeException());

        Reservation reservation = Reservation.createReservation(member, concert
                , request.getOrderName(), request.getExpireAt(), request.getTotalPrice(), request.getAmount());

        reservationRepository.save(reservation);

        return CreateReservationDto.Response.from(reservation);

    }

}
