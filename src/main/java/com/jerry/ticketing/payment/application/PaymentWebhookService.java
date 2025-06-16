package com.jerry.ticketing.payment.application;


import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.seat.application.ConcertSeatCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentWebhookService {

    private final PaymentCommandService paymentCommandService;
    private final ReservationCommandService reservationCommandService;
    private final ConcertSeatCommandService concertSeatCommandService;
    private static final String DONE = "DONE";

    public void handle(WebhookPaymentDto.Request request) {
        WebhookPaymentDto.Request.PaymentData data = request.getData();

        if (DONE.equals(data.getStatus())) {
            finalizeOrder(data);
        }
//
//        if (data.getStatus().equals(DONE)) {
//            finalizeOrder(data);
//        }
//
//        if (data.getStatus().equals(DONE)) {
//            finalizeOrder(data);
//        }
//
//        if (data.getStatus().equals(DONE)) {
//            finalizeOrder(data);
//        }
//
//        if (data.getStatus().equals(DONE)) {
//            finalizeOrder(data);
//        }

//        if(DONE.equals(data.getStatus())) {
//            finalizeOrder(data);
//        }

    }


    /**
     * 토스로부터 Web hook 후 처리
     */
    private void finalizeOrder(WebhookPaymentDto.Request.PaymentData data) {
        paymentCommandService.updatePaymentOnCompleted(data);
        reservationCommandService.confirm(data.getOrderId());
        concertSeatCommandService.confirm(data.getOrderName());
    }

}
