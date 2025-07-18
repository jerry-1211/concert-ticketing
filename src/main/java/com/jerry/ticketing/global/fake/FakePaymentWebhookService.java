package com.jerry.ticketing.global.fake;

import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.seat.application.concertseat.ConcertSeatCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Profile("test")
@RequiredArgsConstructor
public class FakePaymentWebhookService {
    private final FakePaymentCommandService fakePaymentCommandService;
    private final ReservationCommandService reservationCommandService;
    private final ConcertSeatCommandService concertSeatCommandService;


    public void handle(WebhookPaymentDto.Request request) {
        WebhookPaymentDto.Request.PaymentData data = request.getData();

        if (data.getStatus().equals("DONE")) {
            finalizeOrder(data);
        }

    }


    /**
     * 토스로부터 Web hook 후 처리
     */
    private void finalizeOrder(WebhookPaymentDto.Request.PaymentData data) {
        fakePaymentCommandService.updatePaymentOnCompleted(data);
        reservationCommandService.confirm(data.getOrderId());
        concertSeatCommandService.confirm(data.getOrderName());
    }

}