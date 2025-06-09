// 쿼리 파라미터 값을 서버로 전달해 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
// 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
const urlParams = new URLSearchParams(window.location.search);

// 서버로 결제 승인에 필요한 결제 정보를 보내세요.
async function confirm() {
    var requestData = {
        paymentKey: urlParams.get("paymentKey"),
        orderId: urlParams.get("orderId"),
        amount: urlParams.get("amount"),
    };

    const response = await fetch("/api/payment/toss/confirm", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
    });

    const json = await response.json();

    if (!response.ok) {
        throw {message: json.message, code: json.code};
    }

    return json;
}

confirm()
    .then(function (data) {
        // TODO: 결제 승인에 성공했을 경우 UI 처리 로직을 구현하세요.
        document.getElementById("response").innerHTML = `<pre>${JSON.stringify(data, null, 4)}</pre>`;
    })
    .catch((err) => {
        // TODO: 결제 승인에 실패했을 경우 UI 처리 로직을 구현하세요.
        window.location.href = `/fail.html?message=${err.message}&code=${err.code}`;
    });

const paymentKeyElement = document.getElementById("paymentKey");
const orderIdElement = document.getElementById("orderId");
const amountElement = document.getElementById("amount");

orderIdElement.textContent = urlParams.get("orderId");
amountElement.textContent = urlParams.get("amount") + "원";
paymentKeyElement.textContent = urlParams.get("paymentKey");

// 메인 페이지 이동 버튼 이벤트 리스너
document.getElementById('main-button').addEventListener('click', function () {
    window.location.href = '/concerts';
});
