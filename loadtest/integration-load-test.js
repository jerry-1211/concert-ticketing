import http from 'k6/http';
import {check, sleep} from 'k6';
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import {htmlReport} from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import {textSummary} from "https://jslib.k6.io/k6-summary/0.0.1/index.js";

export let options = {
    stages: [
        {duration: '2m', target: 100},
        {duration: '5m', target: 500},
        {duration: '5m', target: 700},
        {duration: '2m', target: 500},
        {duration: '2m', target: 0},
    ],

    batchPerHost: 20,
    http2: false,

    thresholds: {
        http_req_duration: ['p(95)<5000'],
        http_req_failed: ['rate<0.15'],
        'checks{check_name:좌석_선점_성공}': ['rate>0.7'],
    },

    timeout: '30s',
    noConnectionReuse: false,
};

export default function () {
    const concertId = 2;
    const blockedSeatIds = [(43000 * (concertId - 1)) + randomIntBetween(1, 43000)];
    const seatNames = ['A' + randomIntBetween(1, 100), 'B' + randomIntBetween(1, 100)];
    const selectedSeats = seatNames;
    const totalAmount = 100000;
    const accessToken = 'test_token_' + __VU;
    const expireAt = new Date(Date.now() + 15 * 60 * 1000).toISOString();
    const HEADERS = {'Content-Type': 'application/json'};

    // 1. 콘서트 목록 조회
    const concertsResponse = http.get("https://jerry.ngrok.app/concerts");
    check(concertsResponse, {
        '콘서트_목록_조회_성공': (r) => r.status === 200,
    });

    sleep(randomIntBetween(1, 3));

    const seatBlock = {
        concertId: concertId,
        concertSeatIds: blockedSeatIds,
        memberId: 1
    };

    // 2. 좌석 선점 요청
    const seatBlockResponse = http.post(
        "https://jerry.ngrok.app/api/seats/blocks",
        JSON.stringify(seatBlock),
        {headers: HEADERS}
    );

    check(seatBlockResponse, {
        '좌석_선점_성공': (r) => r.status === 200 || r.status === 201,
        '서버_생존': (r) => r.status !== 500,
        '좌석_중복_처리': (r) => r.status === 409,
    });

    sleep(randomIntBetween(1, 3));

    if (seatBlockResponse.status === 200 || seatBlockResponse.status === 201) {
        const reservationData = {
            token: accessToken || null,
            concertId: concertId,
            orderName: `${blockedSeatIds}`,
            expireAt: expireAt,
            totalAmount: totalAmount,
            quantity: selectedSeats.length
        };

        // 3. 예약 요청
        const reservationApiResponse = http.post(
            "https://jerry.ngrok.app/api/reservation",
            JSON.stringify(reservationData),
            {headers: HEADERS}
        );

        check(reservationApiResponse, {
            '예약_성공': (r) => r.status === 200 || r.status === 201,
            '서버_생존': (r) => r.status !== 500
        });

        sleep(randomIntBetween(1, 3));

        if (reservationApiResponse.status === 200 || reservationApiResponse.status === 201) {
            if (!reservationApiResponse.body) {
                return;
            }

            const responseBody = JSON.parse(reservationApiResponse.body);
            const reservationId = responseBody.reservationId;

            const paymentData = {
                reservationId: reservationId,
                totalAmount: totalAmount,
                orderName: `${blockedSeatIds}`
            };

            // 4. 결제 요청
            const paymentApiResponse = http.post(
                "https://jerry.ngrok.app/api/payment/request",
                JSON.stringify(paymentData),
                {headers: HEADERS}
            );

            check(paymentApiResponse, {
                '결제_성공': (r) => r.status === 200 || r.status === 201,
                '서버_생존': (r) => r.status !== 500
            });

            let confirmApiResponse = null;
            if (paymentApiResponse.status === 200 || paymentApiResponse.status === 201) {
                const paymentBody = JSON.parse(paymentApiResponse.body);
                let orderId = paymentBody.orderId;

                const confirmData = {
                    paymentKey: generateRandomString(16),
                    orderId: orderId,
                    amount: totalAmount
                };

                sleep(randomIntBetween(1, 3));

                // 5-1. 성공하는 경우 confirm 호출
                confirmApiResponse = http.post(
                    "https://jerry.ngrok.app/api/payment/toss/confirm",
                    JSON.stringify(confirmData),
                    {headers: HEADERS}
                );

                check(paymentApiResponse, {
                    'confirm_성공': (r) => r.status === 200 || r.status === 201,
                    '서버_생존': (r) => r.status !== 500
                });

                if (!confirmApiResponse || !confirmApiResponse.body) {
                    return;
                }

                const webhookBody = JSON.parse(confirmApiResponse.body);
                const orderName = webhookBody.orderName;
                orderId = webhookBody.orderId;

                const webhookData = {
                    "eventType": "PAYMENT_APPROVED",
                    "createdAt": "2025-07-02T16:00:00Z",
                    "data": {
                        "lastTransactionKey": "TX-" + generateRandomString(6),
                        "paymentKey": "",
                        "orderId": orderId,
                        "orderName": `${blockedSeatIds}`,
                        "status": "DONE",
                        "method": "카드",
                        "totalAmount": totalAmount
                    }
                };

                // 5-2. 웹훅 호출
                let webhookApiResponse = http.post(
                    "https://jerry.ngrok.app/api/webhook/payment",
                    JSON.stringify(webhookData),
                    {headers: HEADERS}
                );

                check(webhookApiResponse, {
                    'webhook_성공': (r) => r.status === 200 || r.status === 201,
                    '서버_생존': (r) => r.status !== 500
                });

                sleep(randomIntBetween(1, 3));
            }
        }

        sleep(randomIntBetween(1, 3));
    } else {
        return;
    }
}

export function handleSummary(data) {
    return {
        "/app/results/result.json": JSON.stringify(data),
        "/app/results/report.html": htmlReport(data),
        "stdout": textSummary(data, {indent: " ", enableColors: true}),
    };
}

function generateRandomString(length) {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';
    for (let i = 0; i < length; i++) {
        result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return result;
}
