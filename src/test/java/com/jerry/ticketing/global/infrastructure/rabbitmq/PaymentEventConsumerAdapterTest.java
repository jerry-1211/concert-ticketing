package com.jerry.ticketing.global.infrastructure.rabbitmq;

import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.port.PaymentEventConsumerPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PaymentEventConsumerAdapterTest {

    @Mock
    public PaymentEventConsumerPort paymentEventConsumerPort;

    @InjectMocks
    public PaymentEventConsumerAdapter paymentEventConsumerAdapter;


    @Test
    @DisplayName("handleConfirmEvent 메서드의 포트 호출을 확인한다.")
    void checkThatConfirmPortIsInvokedInHandleConfirmEvent() {
        // given
        ConfirmPaymentDto.Request request = new ConfirmPaymentDto.Request("paymentkey123", "orderId123", "10000");

        // when
        paymentEventConsumerAdapter.handleConfirmEvent(request);

        // then
        verify(paymentEventConsumerPort).handleConfirmEvent(request);
        verifyNoMoreInteractions(paymentEventConsumerPort);
    }

    @Test
    @DisplayName("handleWebhookEvent 메서드의 포트 호출을 확인한다.")
    void checkThatWebhookPortIsInvokedInHandleWebhookEvent() {
        // given
        WebhookPaymentDto.Request.PaymentData data = WebhookPaymentDto.Request.PaymentData.of(
                "lastTransactionKeylastTransactionKey123", "orderName123",
                "method123", 1_000
        );

        // when
        paymentEventConsumerAdapter.handleWebhookEvent(data);

        // then
        verify(paymentEventConsumerPort).handleWebhookEvent(data);
        verifyNoMoreInteractions(paymentEventConsumerPort);
    }


}