package com.jerry.ticketing.global.infrastructure.rabbitmq;


import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.port.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
@Profile("!test")
@RabbitListener(queues = "payment.process.queue")
@Slf4j
public class PaymentEventConsumer {

    private final PaymentRepository paymentRepository;


    @RabbitHandler
    @Transactional
    public void handleConfirmEvent(ConfirmPaymentDto.Request request) {

        Payment payment = paymentRepository.findByOrderId(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());
    }

    @RabbitHandler
    @Transactional
    public void handleWebhookEvent(WebhookPaymentDto.Request.PaymentData data) {
        OffsetDateTime dateTime = OffsetDateTime.now();
        Payment payment = paymentRepository.findByOrderId(data.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.complete(data, dateTime);
    }

}
