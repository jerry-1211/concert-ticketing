package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.global.util.PaymentOrderIdGenerator;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.infrastructure.external.TossPaymentClient;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.payment.application.dto.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.PaymentWebhookDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.infrastructure.repository.ReservationRepository;
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
    public CreatePaymentDto.Response createPayment(CreatePaymentDto.Request request){
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        String orderId = PaymentOrderIdGenerator.generate(reservation.getId());
        reservation.updateOrderId(orderId);

        Payment payment = Payment.createTossPayment(reservation, orderId);
        Payment savedPayment = paymentRepository.save(payment);

        return CreatePaymentDto.Response.from(savedPayment);
    }


    /**
     * 결제 확인
     * */
    @Transactional
    public CreatePaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {
        ConfirmPaymentDto.Response response = tossPaymentClient.confirmPayment(request);

        Payment payment = paymentRepository.findByOrderIdWithLock(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());


        log.info("결제 승인 완료 - PaymentKey: {}", request.getPaymentKey());

        return CreatePaymentDto.Response.from(response, payment);
    }

    /**
     * Webhook 처리 후 업데이트
     * */
    @Transactional
    public void updatePaymentOnCompleted(PaymentWebhookDto.Request.PaymentData data){
        Payment payment = paymentRepository.findByOrderIdWithLock(data.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.completed(data);

    }


}
