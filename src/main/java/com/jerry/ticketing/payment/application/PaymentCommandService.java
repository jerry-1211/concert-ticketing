package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.payment.domain.port.PaymentEventConsumerPort;
import com.jerry.ticketing.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentCommandService implements PaymentEventConsumerPort {

    private final PaymentRepository paymentRepository;
    private final PaymentQueryService paymentQueryService;
    private final PaymentFacade paymentFacade;

    @Transactional
    public CreatePaymentDto.Response createPayment(CreatePaymentDto.Request request) {
        Payment payment = paymentFacade.create(request.getReservationId());
        Payment savedPayment = paymentRepository.save(payment);
        return paymentQueryService.getDetailed(savedPayment.getId());
    }


    @Transactional
    public CreatePaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {
        paymentFacade.confirm(request);
        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        return paymentQueryService.getDetailed(payment.getId());
    }


    @Transactional
    public void updatePaymentOnCompleted(WebhookPaymentDto.Request.PaymentData data) {
        paymentFacade.update(data);
    }


    @Override
    @Transactional
    public void handleWebhookEvent(WebhookPaymentDto.Request.PaymentData data) {
        Payment payment = paymentRepository.findByOrderId(data.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.complete(data);
    }

    @Override
    @Transactional
    public void handleConfirmEvent(ConfirmPaymentDto.Request request) {
        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());
    }
}
