package com.jerry.ticketing.payment.application;


import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.seat.application.ConcertSeatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookService {

    private final PaymentCommandService paymentCommandService;
    private final ReservationCommandService reservationCommandService;
    private final ConcertSeatCommandService concertSeatCommandService;


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

        paymentCommandService.updatePaymentOnCompleted(data);
        reservationCommandService.confirmReservation(data.getOrderId());
        concertSeatCommandService.confirmConcertSeat(data.getOrderName());

        log.info("결제 승인 완료 - OrderID: {}", data.getOrderId());
    }

}
