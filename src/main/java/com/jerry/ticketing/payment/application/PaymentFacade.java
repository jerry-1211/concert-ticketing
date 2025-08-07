package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.port.PaymentMessagePublisherPort;
import com.jerry.ticketing.payment.infrastructure.external.TossPaymentClient;
import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentMessagePublisherPort paymentMessagePublisherPort;
    private final ReservationCommandService reservationCommandService;
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


}
