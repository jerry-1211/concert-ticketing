package com.jerry.ticketing.application.payment;

import com.jerry.ticketing.domain.payment.Payment;
import com.jerry.ticketing.domain.payment.enums.PaymentMethod;
import com.jerry.ticketing.domain.payment.enums.PaymentStatus;
import com.jerry.ticketing.domain.reservation.Reservation;
import com.jerry.ticketing.dto.request.PaymentRequest;
import com.jerry.ticketing.dto.response.PaymentResponse;
import com.jerry.ticketing.dto.response.TossPaymentResponse;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.PaymentErrorCode;
import com.jerry.ticketing.exception.ReservationErrorCode;
import com.jerry.ticketing.repository.payment.PaymentRepository;
import com.jerry.ticketing.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

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
    public PaymentResponse createPayment(PaymentRequest request){
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new BusinessException(ReservationErrorCode.RESERVATION_NOT_FOUND));

        String idempotentKey = generateIdempotentKey(reservation.getId());

        Payment payment = Payment.builder()
                .reservation(reservation)
                .paymentMethod(PaymentMethod.TOSSPAY)
                .paymentStatus(PaymentStatus.PENDING)
                .paymentDate(OffsetDateTime.now())
                .amount(reservation.getTotalPrice())
                .idempotencyKey(idempotentKey)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.from(savedPayment);
    }


    private String generateIdempotentKey(Long reservationId) {
        return "RES-" + reservationId + "-" + UUID.randomUUID().toString().substring(0, 8);
    }


    public PaymentResponse confirmTossPayment(String paymentKey, String orderId, String amount) {
        TossPaymentResponse tossPaymentResponse = tossPaymentClient.confirmPayment(paymentKey, orderId, amount);

        Payment payment = paymentRepository.findByIdempotencyKey(orderId)
                .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        payment.updateStatus(PaymentStatus.COMPLETED);
        payment.updatePaymentDate(OffsetDateTime.now());

        Reservation reservation = payment.getReservation();
        reservation.confirmReservation();

        log.info("결제 승인 완료 - PaymentKey: {}, OrderID: {}", paymentKey,orderId);
        return PaymentResponse.from(payment, tossPaymentResponse);
    }
}
