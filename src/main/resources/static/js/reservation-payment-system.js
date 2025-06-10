// ------  결제 시스템 SDK 초기화 ------
const clientKey = "test_ck_LlDJaYngro9wkzG7kWZK3ezGdRpX";
const customerKey = "_VpSOj_EwRTCArg_8m6cC";
const tossPayments = TossPayments(clientKey);
const payment = tossPayments.payment({customerKey});

/**
 * 예약 생성 및 결제 처리
 * @param {Object} result - 좌석 블록 결과
 * @param {Array} selectedSeats - 선택된 좌석 배열
 * @param {number} concertId - 콘서트 ID
 */
async function reservationConcertSeat(result, selectedSeats, concertId) {
    try {
        const blockedSeatIds = result.blockedSeatIds;
        const totalPrice = result.totalPrice;
        const expireAt = result.expireAt;
        const seatNames = selectedSeats.map(seat => `${seat.zone}-${seat.row}-${seat.seatNumber}`).join(', ');

        const response = await fetch('/api/reservation', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                memberId: 1, // 하드 코딩
                concertId: concertId,
                orderName: `${blockedSeatIds} - ${seatNames}`,
                expireAt: expireAt,
                totalPrice: totalPrice,
                amount: selectedSeats.length
            })
        });

        const reservationData = await response.json();
        console.log(reservationData);

        if (response.ok) {
            await processPayment(reservationData);
        } else {
            const error = await response.text();
            alert('예약 실패: ' + error);
        }
    } catch (error) {
        console.error("예약 실패: ", error);
        throw error;
    }
}

/**
 * 결제 처리
 * @param {Object} result - 예약 결과 데이터
 */
async function processPayment(result) {
    console.log("processPayment 호출 완료 ! ");

    try {
        const orderName = result.orderName;
        const totalPrice = result.totalPrice;
        const reservationId = result.reservationId;

        const response = await fetch('/api/payment/request', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                reservationId: reservationId,
                totalPrice: totalPrice,
                orderName: orderName
            })
        });

        const paymentData = await response.json();

        await payment.requestPayment({
            method: "CARD",
            amount: {
                currency: "KRW",
                value: totalPrice,
            },
            orderId: paymentData.orderId,
            orderName: orderName,
            successUrl: paymentData.successUrl,
            failUrl: paymentData.failUrl,
            customerEmail: paymentData.customerEmail,
            customerName: paymentData.customerName,
            customerMobilePhone: paymentData.customerMobilePhone,
            card: {
                useEscrow: false,
                flowMode: "DEFAULT",
                useCardPoint: false,
                useAppCardOnly: false,
            },
        });
    } catch (error) {
        console.error("결제 요청 실패: ", error);
        throw error;
    }
}

/**
 * 결제 설정 정보 가져오기
 */
function getPaymentConfig() {
    return {
        clientKey,
        customerKey,
        tossPayments,
        payment
    };
}

// 전역으로 사용할 수 있도록 export
window.PaymentSystem = {
    reservationConcertSeat,
    processPayment,
    getPaymentConfig
};
