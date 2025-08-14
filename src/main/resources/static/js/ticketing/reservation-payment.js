// ------  결제 시스템 SDK 초기화 ------
const clientKey = "test_ck_LlDJaYngro9wkzG7kWZK3ezGdRpX";
const customerKey = "_VpSOj_EwRTCArg_8m6cC";
const tossPayments = TossPayments(clientKey);
const payment = tossPayments.payment({customerKey});


async function reservationConcertSeat(result, selectedSeats, concertId) {
    try {
        const blockedSeatIds = result.blockedSeatIds;
        const totalAmount = result.totalAmount;
        const expireAt = result.expireAt;
        const seatNames = selectedSeats.map(seat => `${seat.zone}-${seat.row}-${seat.concertSeatId}`).join(', ');
        const accessToken = localStorage.getItem('accessToken');


        const response = await fetch('/api/reservation', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                token: accessToken || null,
                concertId: concertId,
                orderName: `${blockedSeatIds} - ${seatNames}`,
                expireAt: expireAt,
                totalAmount: totalAmount,
                quantity: selectedSeats.length
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


async function processPayment(result) {

    try {
        const orderName = result.orderName;
        const totalAmount = result.totalAmount;
        const reservationId = result.reservationId;

        const response = await fetch('/api/payment/request', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                reservationId: reservationId,
                totalAmount: totalAmount,
                orderName: orderName
            })
        });


        const responseText = await response.text();
        const paymentData = JSON.parse(responseText);

        if (paymentData.profile !== "test") {
            await payment.requestPayment({
                method: "CARD",
                amount: {
                    currency: "KRW",
                    value: totalAmount,
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
        }


    } catch (error) {
        console.error("결제 요청 실패: ", error);
        throw error;
    }
}


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
