package com.jerry.ticketing.repository.payment;

import com.jerry.ticketing.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
