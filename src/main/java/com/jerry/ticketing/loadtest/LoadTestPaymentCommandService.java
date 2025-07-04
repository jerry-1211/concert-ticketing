package com.jerry.ticketing.loadtest;

import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.payment.application.PaymentQueryService;
import com.jerry.ticketing.payment.application.dto.web.ConfirmPaymentDto;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.reservation.application.ReservationCommandService;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Profile("test")
@RequiredArgsConstructor
public class LoadTestPaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final PaymentQueryService paymentQueryService;
    private final ReservationCommandService reservationCommandService;

    @Transactional
    public CreatePaymentDto.Response createPayment(CreatePaymentDto.Request request) {

        ReservationDto reservation = reservationCommandService.updateOrderId(request);
        Payment savedPayment = paymentRepository.save(Payment.createTossPayment(reservation.getReservationId(), reservation.getOrderId()));

        return paymentQueryService.getDetailedPayment(savedPayment.getId());
    }


    @Transactional
    public CreatePaymentDto.Response confirmPayment(ConfirmPaymentDto.Request request) {

        Payment payment = paymentRepository.findByOrderIdWithLock(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateConfirm(request.getPaymentKey());

        return paymentQueryService.getDetailedPayment(payment.getId());
    }


}
