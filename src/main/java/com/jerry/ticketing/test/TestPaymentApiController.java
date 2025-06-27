package com.jerry.ticketing.test;

import com.jerry.ticketing.payment.application.PaymentQueryService;
import com.jerry.ticketing.payment.application.dto.web.CreatePaymentDto;
import com.jerry.ticketing.payment.domain.Payment;
import com.jerry.ticketing.payment.infrastructure.config.TossPaymentConfig;
import com.jerry.ticketing.payment.infrastructure.repository.PaymentRepository;
import com.jerry.ticketing.payment.util.PaymentOrderIdGenerator;
import com.jerry.ticketing.reservation.application.ReservationQueryService;
import com.jerry.ticketing.reservation.domain.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@Profile("test")
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class TestPaymentApiController {

    private final ReservationQueryService reservationQueryService;
    private final PaymentQueryService paymentQueryService;
    private final TossPaymentConfig tossPaymentConfig;
    private final PaymentRepository paymentRepository;


    @PostMapping("/request")
    public ResponseEntity<Void> TestCreatePayment(@Valid @RequestBody CreatePaymentDto.Request request) {
        Reservation reservation = reservationQueryService.getReservation(request.getReservationId(), Function.identity());
        String orderId = PaymentOrderIdGenerator.generate(reservation.getId());

        Payment savedPayment = paymentRepository.save(Payment.createTossPayment(reservation.getId(), orderId));

        CreatePaymentDto.Response response = paymentQueryService.getDetailedPayment(savedPayment.getId());
        response.setPaymentUrls(tossPaymentConfig);

        return ResponseEntity.ok().build();

    }


}
