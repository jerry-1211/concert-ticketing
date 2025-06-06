package com.jerry.ticketing.application.payment;


import com.jerry.ticketing.application.reservation.ReservationService;
import com.jerry.ticketing.dto.TossPaymentWebhook;
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



    public void handleWebhook(TossPaymentWebhook.Request request){
        TossPaymentWebhook.Request.PaymentData data = request.getData();

        if(data.getStatus().equals("DONE")){
            finalizeOrder(data);
        }

    }


    /**
     * Web hook 결제 후 처리
     * */
    private void finalizeOrder(TossPaymentWebhook.Request.PaymentData data) {

        paymentService.updatePaymentOnCompleted(data);
        reservationService.confirmReservation(data.getOrderId());

        log.info("결제 승인 완료 - OrderID: {}", data.getOrderId());
    }

}
