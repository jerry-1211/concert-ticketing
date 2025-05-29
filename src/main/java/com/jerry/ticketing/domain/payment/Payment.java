package com.jerry.ticketing.domain.payment;


import com.jerry.ticketing.domain.payment.enums.PaymentMethod;
import com.jerry.ticketing.domain.payment.enums.PaymentStatus;
import com.jerry.ticketing.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    // 결제 id
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예약 id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // 결제 수단
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // 결제 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    // 결제 날짜
    @Column(nullable = false)
    private OffsetDateTime paymentDate;

    // 결제 멱등성
    @Column(nullable = false)
    private String idempotencyKey;



    private Payment(Reservation reservation, PaymentMethod paymentMethod,
                   PaymentStatus paymentStatus, OffsetDateTime paymentDate, String idempotencyKey) {
        this.reservation = reservation;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.idempotencyKey = idempotencyKey;
    }

    public static Payment createPayment(Reservation reservation, PaymentMethod paymentMethod,
                                PaymentStatus paymentStatus, OffsetDateTime paymentDate, String idempotencyKey){
        return new Payment(reservation, paymentMethod, paymentStatus, paymentDate, idempotencyKey);
    }

    public void updateStatus(PaymentStatus status){
        paymentStatus = status;
    }

    public void updatePaymentDate(OffsetDateTime time){
        paymentDate = time;
    }
}
