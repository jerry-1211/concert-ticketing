package com.jerry.ticketing.payment.application;

import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.payment.application.dto.domain.PaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.domain.port.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentQueryServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentFacade paymentFacade;

    @Mock
    private Payment mockPayment;

    @InjectMocks
    private PaymentQueryService paymentQueryService;


    @Test
    @DisplayName("결제 ID로 결제를 찾은 후, 결제 세부 사항을 가져오는 메서드를 호출한다. ")
    void callGetDetailedPaymentByPaymentId() {
        // given
        Long paymentId = 1L;
        CreatePaymentDto.Response createPaymentDto = CreatePaymentDto.Response.builder().paymentId(1L).build();

        given(paymentRepository.findById(paymentId)).willReturn(Optional.of(mockPayment));
        given(paymentFacade.resolve(any(PaymentDto.class))).willReturn(createPaymentDto);

        // when
        CreatePaymentDto.Response response = paymentQueryService.getDetailed(paymentId);

        // then
        assertThat(response).isNotNull();
        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentFacade, times(1)).resolve(any(PaymentDto.class));
    }

    @Test
    @DisplayName("결제 ID에 맞는 결제가 없는 경우, 결제 에러를 발생 시킨다.")
    void throwsExceptionWhenPaymentNotFound() {
        // given
        Long paymentId = 1L;

        given(paymentRepository.findById(paymentId)).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> paymentQueryService.getDetailed(paymentId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", PaymentErrorCode.PAYMENT_NOT_FOUND);
    }


}