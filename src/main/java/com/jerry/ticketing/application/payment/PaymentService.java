package com.jerry.ticketing.application.payment;

import com.jerry.ticketing.application.payment.util.PaymentOrderIdGenerator;
import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.domain.reservation.Reservation;
import com.jerry.ticketing.dto.ConfirmTossPayment;
import com.jerry.ticketing.dto.CreatePayment;
import com.jerry.ticketing.dto.TossPaymentWebhook;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.repository.payment.PaymentRepository;
import com.jerry.ticketing.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final TossPaymentClient tossPaymentClient;

    /**
     * 결제 요청 생성
     * */
    @Transactional
    public CreatePayment.Response createPayment(CreatePayment.Request request){
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        String orderId = PaymentOrderIdGenerator.generate(reservation.getId());
        reservation.updateOrderId(orderId);

        Payment payment = Payment.createTossPayment(reservation, orderId);
        Payment savedPayment = paymentRepository.save(payment);

        return CreatePayment.Response.from(savedPayment);
    }


    /**
     * 결제 확인
     * */
    @Transactional
    public CreatePayment.Response confirmPayment(ConfirmTossPayment.Request request) {
        ConfirmTossPayment.Response response = tossPaymentClient.confirmPayment(request);

        Payment payment = paymentRepository.findByOrderIdWithLock(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());


        log.info("결제 승인 완료 - PaymentKey: {}", request.getPaymentKey());

        return CreatePayment.Response.from(response, payment);
    }

    /**
     * Webhook 처리 후 업데이트
     * */
    @Transactional
    public void updatePaymentOnCompleted(TossPaymentWebhook.Request.PaymentData data){
        Payment payment = paymentRepository.findByOrderIdWithLock(data.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.completed(data);

    }


}
