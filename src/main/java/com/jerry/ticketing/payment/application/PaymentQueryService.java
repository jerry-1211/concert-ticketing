package com.jerry.ticketing.payment.application;


import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.member.application.MemberService;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.domain.PaymentDto;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {
    private final PaymentRepository paymentRepository;
    private final ReservationQueryService reservationQueryService;
    private final MemberService memberService;
    private final ConcertQueryService concertQueryService;

    @Transactional(readOnly = true)
    public CreatePaymentDto.Response findDetailedPaymentById(Long paymentId) {
        PaymentDto payment = findPaymentById(paymentId);
        ReservationDto reservation = reservationQueryService.findReservationDtoById(payment.getReservationId());
        MemberDto member = memberService.findMemberById(reservation.getMemberId());
        ConcertDto concert = concertQueryService.findConcertById(reservation.getConcertId());

        return CreatePaymentDto.Response.from(payment, member, concert);
    }

    @Transactional(readOnly = true)
    public PaymentDto findPaymentById(Long paymentId) {
        return PaymentDto.from(paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND)));
    }
}
