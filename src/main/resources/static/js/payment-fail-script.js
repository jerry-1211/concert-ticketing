const urlParams = new URLSearchParams(window.location.search);

const codeElement = document.getElementById("code");
const messageElement = document.getElementById("message");

codeElement.textContent = urlParams.get("code") || "알 수 없는 오류";
messageElement.textContent = urlParams.get("message") || "결제 처리 중 문제가 발생했습니다.";
