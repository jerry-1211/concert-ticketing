package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.application.dto.web.WebhookPaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.port.PaymentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentCommandServiceTest {

    @Mock
    private PaymentFacade paymentFacade;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentQueryService paymentQueryService;

    @Mock
    private Payment mockPayment;


    @InjectMocks
    private PaymentCommandService paymentCommandService;


    @Test
    @DisplayName("파라미터(결제 생성 요청)로 결제의 생성,저장,세부사항 메서드를 호출한다. ")
    void callPaymentCreationRequest() {
        // given
        CreatePaymentDto.Request request = CreatePaymentDto.Request.of(1L, 100_000, "orderName-123");
        CreatePaymentDto.Response response = CreatePaymentDto.Response.builder().paymentId(1L).build();

        given(paymentFacade.create(request.getReservationId())).willReturn(mockPayment);
        given(paymentRepository.save(mockPayment)).willReturn(mockPayment);
        given(mockPayment.getId()).willReturn(1L);
        given(paymentQueryService.getDetailedPayment(mockPayment.getId())).willReturn(response);


        // when
        paymentCommandService.createPayment(request);

        // then
        verify(paymentFacade, times(1)).create(request.getReservationId());
        verify(paymentRepository, times(1)).save(mockPayment);
        verify(paymentQueryService, times(1)).getDetailedPayment(mockPayment.getId());
    }


    @Test
    @DisplayName("파라미터(결제 확정 요청)로 결제의 확정,메시지 이벤트,세부사항 메서드를 호출한다.")
    void callPaymentConfirmationRequest() {
        // given
        ConfirmPaymentDto.Request request = ConfirmPaymentDto.Request.of("paymentKey-123", "oderId-123", 100_00);
        CreatePaymentDto.Response response = CreatePaymentDto.Response.builder().paymentId(1L).build();

        given(paymentRepository.findByOrderId(request.getOrderId())).willReturn(Optional.of(mockPayment));
        given(paymentQueryService.getDetailedPayment(mockPayment.getId())).willReturn(response);


        // when
        paymentCommandService.confirmPayment(request);

        // then
        verify(paymentFacade, times(1)).confirm(request);
        verify(paymentRepository, times(1)).findByOrderId(request.getOrderId());
        verify(paymentQueryService, times(1)).getDetailedPayment(mockPayment.getId());
    }

    @Test
    @DisplayName("파라미터(웹훅 요청 데이터)로 업데이트 메서드를 호출한다.")
    void callWebhookUpdate() {
        // given
        WebhookPaymentDto.Request.PaymentData data = WebhookPaymentDto.Request.PaymentData
                .of("lastTransactionKey-123", "orderName-123", "method1", 1_000);

        // when
        paymentCommandService.updatePaymentOnCompleted(data);

        // then
        verify(paymentFacade, times(1)).update(data);
    }


    @Test
    @DisplayName("파라미터(웹훅 요청 데이터)로 결제를 찾아 결제 확정 메서드를 호출한다.")
    void callConfirmPaymentFromWebhook() {
        // given
        WebhookPaymentDto.Request.PaymentData data = WebhookPaymentDto.Request.PaymentData
                .of("lastTransactionKey-123", "orderName-123", "method1", 1_000);
        given(paymentRepository.findByOrderId(data.getOrderId())).willReturn(Optional.of(mockPayment));

        // when
        paymentCommandService.handleWebhookEvent(data);

        // then
        verify(paymentRepository, times(1)).findByOrderId(data.getOrderId());
        verify(mockPayment, times(1)).complete(data);
    }

    @Test
    @DisplayName("파라미터(결제 확정 요청)로 결제 객체를 찾아 업데이트 메서드를 호출한다.")
    void callUpdatePaymentFromConfirmationRequest() {
        // given
        ConfirmPaymentDto.Request request = ConfirmPaymentDto.Request.of("paymentKey-123", "oderId-123", 100_00);
        given(paymentRepository.findByOrderId(request.getOrderId())).willReturn(Optional.of(mockPayment));

        // when
        paymentCommandService.handleConfirmEvent(request);

        // then
        verify(paymentRepository, times(1)).findByOrderId(request.getOrderId());
        verify(mockPayment, times(1)).updateConfirm(request.getPaymentKey());
    }

    @Test
    @DisplayName("파라미터(결제 확정 요청)에 맞는 결제 객체가 없는 경우 결제 에러코드를 발생시킨다.")
    void ThrowPaymentNotFoundError() {
        // given
        ConfirmPaymentDto.Request request = ConfirmPaymentDto.Request.of("paymentKey-123", "oderId-123", 100_00);
        given(paymentRepository.findByOrderId(request.getOrderId())).willReturn(Optional.empty());

        // when // then
        Assertions.assertThatThrownBy(() -> paymentCommandService.handleConfirmEvent(request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", PaymentErrorCode.PAYMENT_NOT_FOUND);
    }


}