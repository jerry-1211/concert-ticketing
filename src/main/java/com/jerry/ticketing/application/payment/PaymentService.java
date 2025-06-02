package com.jerry.ticketing.application.payment;

import com.jerry.ticketing.application.payment.util.PaymentIdempotentKeyGenerator;
import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.domain.payment.enums.PaymentStatus;
import com.jerry.ticketing.domain.reservation.Reservation;
import com.jerry.ticketing.dto.ConfirmTossPayment;
import com.jerry.ticketing.dto.CreatePayment;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.PaymentErrorCode;
import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.repository.payment.PaymentRepository;
import com.jerry.ticketing.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final TossPaymentClient tossPaymentClient;

    /**
     * 결제 요청 생성
     * */
    public CreatePayment.Response createPayment(CreatePayment.Request request){
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        String idempotentKey = PaymentIdempotentKeyGenerator.generate(reservation.getId());

        Payment payment = Payment.createTossPayment(reservation, idempotentKey);
        Payment savedPayment = paymentRepository.save(payment);

        return CreatePayment.Response.from(savedPayment);
    }



    /**
     * 결제 확인
     * */
    public CreatePayment.Response confirmPayment(ConfirmTossPayment.Request request) {
        ConfirmTossPayment.Response response = tossPaymentClient.confirmPayment(request);

        Payment payment = paymentRepository.findByIdempotencyKey(request.getOrderId())
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateStatus(PaymentStatus.COMPLETED);
        payment.updatePaymentDate();

        Reservation reservation = payment.getReservation();
        reservation.confirmReservation();

        log.info("결제 승인 완료 - PaymentKey: {}, OrderID: {}", request.getPaymentKey(),request.getOrderId());
        return CreatePayment.Response.from(response, payment);
    }

}
