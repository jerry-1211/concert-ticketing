const urlParams = new URLSearchParams(window.location.search);

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


// 기존 코드는 그대로 두고, 맨 아래에 추가
document.getElementById('toggleResponse').addEventListener('click', function () {
    const responseContainer = document.getElementById('responseContainer');
    const toggleBtn = this;

    if (responseContainer.style.display === 'none') {
        responseContainer.style.display = 'block';
        toggleBtn.classList.add('active');
    } else {
        responseContainer.style.display = 'none';
        toggleBtn.classList.remove('active');
    }
});

// 개발자 정보 헤더 클릭 시에도 토글
document.querySelector('.dev-header').addEventListener('click', function () {
    document.getElementById('toggleResponse').click();
});

