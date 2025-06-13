package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.payment.util.PaymentOrderIdGenerator;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.infrastructure.external.TossPaymentClient;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final PaymentQueryService paymentQueryService;
    private final TossPaymentClient tossPaymentClient;
    private final ReservationQueryService reservationQueryService;

    @Transactional
    public CreatePaymentDto.Response createPayment(CreatePaymentDto.Request request) {

        Reservation reservation = reservationQueryService.findReservationById(request.getReservationId(), Function.identity());

        String orderId = PaymentOrderIdGenerator.generate(reservation.getId());
        reservation.updateOrderId(orderId);

        Payment savedPayment = paymentRepository.save(Payment.createTossPayment(reservation.getId(), orderId));

        return paymentQueryService.findDetailedPaymentById(savedPayment.getId());
    }


    @Transactional
    public CreatePaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {

        tossPaymentClient.confirmPayment(request);

        Payment payment = paymentRepository.findByOrderIdWithLock(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());

        return paymentQueryService.findDetailedPaymentById(payment.getId());
    }

    /**
     * Webhook 처리 후 업데이트
     */
    @Transactional
    public void updatePaymentOnCompleted(WebhookPaymentDto.Request.PaymentData data) {
        Payment payment = paymentRepository.findByOrderIdWithLock(data.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.completed(data);
    }


}
