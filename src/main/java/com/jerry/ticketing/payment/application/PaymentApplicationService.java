package com.jerry.ticketing.payment.application;


import com.jerry.ticketing.concert.application.ConcertService;
import com.jerry.ticketing.concert.application.dto.ConcertDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.member.application.MemberService;
import com.jerry.ticketing.member.application.dto.MemberDto;
import com.jerry.ticketing.payment.application.dto.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.PaymentDto;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.application.ReservationService;
import com.jerry.ticketing.reservation.application.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {
    private final PaymentRepository paymentRepository;
    private final ReservationService reservationService;
    private final MemberService memberService;
    private final ConcertService concertService;

    @Transactional
    public CreatePaymentDto.Response getPaymentDetail(Long paymentId) {
        PaymentDto payment = findPaymentById(paymentId);
        ReservationDto reservation = reservationService.findReservationById(payment.getReservationId());
        MemberDto member = memberService.findMemberById(reservation.getMemberId());
        ConcertDto concert = concertService.findConcertById(reservation.getConcertId());

        return CreatePaymentDto.Response.from(payment, member, concert);
    }

    @Transactional
    public PaymentDto findPaymentById(Long paymentId) {
        return PaymentDto.from(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND)));
    }
}
