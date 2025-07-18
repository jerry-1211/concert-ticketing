package com.jerry.ticketing.payment.domain.port;

import com.jerry.ticketing.payment.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByOrderId(String orderId);

}
