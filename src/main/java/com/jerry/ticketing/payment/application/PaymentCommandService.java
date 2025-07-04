package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.infrastructure.external.TossPaymentClient;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final PaymentQueryService paymentQueryService;
    private final ReservationCommandService reservationCommandService;
    private final TossPaymentClient tossPaymentClient;

    @Transactional
    public CreatePaymentDto.Response createPayment(CreatePaymentDto.Request request) {

        ReservationDto reservation = reservationCommandService.updateOrderId(request);
        Payment savedPayment = paymentRepository.save(Payment.createTossPayment(reservation.getReservationId(), reservation.getOrderId()));

        return paymentQueryService.getDetailedPayment(savedPayment.getId());
    }


    @Transactional
    public CreatePaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {

        tossPaymentClient.confirmPayment(request);

        Payment payment = paymentRepository.findByOrderIdWithLock(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());

        return paymentQueryService.getDetailedPayment(payment.getId());
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
