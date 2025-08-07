package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.concert.application.ConcertQueryService;
import com.jerry.ticketing.concert.application.dto.domain.ConcertDto;
import com.jerry.ticketing.member.application.MemberQueryService;
import com.jerry.ticketing.member.application.dto.domain.MemberDto;
import com.jerry.ticketing.payment.application.dto.domain.PaymentDto;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.port.PaymentMessagePublisherPort;
import com.jerry.ticketing.payment.infrastructure.external.TossPaymentClient;
import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentMessagePublisherPort paymentMessagePublisherPort;
    private final ReservationQueryService reservationQueryService;
    private final ReservationCommandService reservationCommandService;
    private final MemberQueryService memberQueryService;
    private final ConcertQueryService concertQueryService;
    private final TossPaymentClient tossPaymentClient;


    public Payment create(Long reservationId) {
        OffsetDateTime dateTime = OffsetDateTime.now();
        ReservationDto reservation = reservationCommandService.updateOrderId(reservationId);
        return Payment.createTossPayment(reservation.getReservationId(), reservation.getOrderId(), dateTime);
    }

    public void confirm(ConfirmPaymentDto.Request request) {
        tossPaymentClient.confirmPayment(request);
        paymentMessagePublisherPort.publishConfirmEvent(request);
    }


    public void update(WebhookPaymentDto.Request.PaymentData data) {
        paymentMessagePublisherPort.publishWebhookEvent(data);
    }

    public CreatePaymentDto.Response resolve(PaymentDto payment) {
        ReservationDto reservation = reservationQueryService.getReservation(payment.getReservationId(), ReservationDto::from);
        MemberDto member = memberQueryService.getMemberById(reservation.getMemberId(), MemberDto::from);
        ConcertDto concert = concertQueryService.getConcertById(reservation.getConcertId(), ConcertDto::from);
        return CreatePaymentDto.Response.from(payment, member, concert);
    }


}
