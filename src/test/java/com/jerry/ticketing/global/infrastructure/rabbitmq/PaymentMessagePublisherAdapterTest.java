package com.jerry.ticketing.global.infrastructure.rabbitmq;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentMessagePublisherAdapterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PaymentMessagePublisherAdapter paymentMessagePublisher;


    @Test
    @DisplayName("publishConfirmEvent 메서드에서 RabbitMQ의 호출을 확인한다.")
    void checkRabbitMQCallInPublishConfirmEventMethod() {
        // given
        ConfirmPaymentDto.Request request = ConfirmPaymentDto.Request.of("paymentkey123", "orderId123", 10000);

        // when
        paymentMessagePublisher.publishConfirmEvent(request);

        // then
        verify(rabbitTemplate).convertAndSend(eq("payment.exchange"), eq("payment.confirm"), eq(request));
    }


    @Test
    @DisplayName("publishWebhookEvent 메서드에서 RabbitMQ의 호출을 확인한다.")
    void checkRabbitMQCallInPublishWebhookEventMethod() {
        // given
        WebhookPaymentDto.Request.PaymentData data = WebhookPaymentDto.Request.PaymentData.of(
                "lastTransactionKeylastTransactionKey123", "orderName123",
                "method123", 1_000
        );

        // when
        paymentMessagePublisher.publishWebhookEvent(data);

        // then
        verify(rabbitTemplate).convertAndSend(eq("payment.exchange"), eq("payment.webhook"), eq(data));
    }

}