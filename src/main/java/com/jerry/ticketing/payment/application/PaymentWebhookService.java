package com.jerry.ticketing.payment.application;


import com.jerry.ticketing.reservation.application.ReservationService;
import com.jerry.ticketing.payment.application.dto.WebhookPaymentDto;
import com.jerry.ticketing.seat.application.ConcertSeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookService {

    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final ConcertSeatService concertSeatService;


    public void handleWebhook(WebhookPaymentDto.Request request) {
        WebhookPaymentDto.Request.PaymentData data = request.getData();

        if (data.getStatus().equals("DONE")) {
            finalizeOrder(data);
        }

    }


    /**
     * Web hook 결제 후 처리
     */
    private void finalizeOrder(WebhookPaymentDto.Request.PaymentData data) {

        paymentService.updatePaymentOnCompleted(data);
        reservationService.confirmReservation(data.getOrderId());
        concertSeatService.confirmConcertSeat(data.getOrderName());

        log.info("결제 승인 완료 - OrderID: {}", data.getOrderId());
    }

}
