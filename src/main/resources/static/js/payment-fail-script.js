const urlParams = new URLSearchParams(window.location.search);

const codeElement = document.getElementById("code");
const messageElement = document.getElementById("message");

codeElement.textContent = "에러코드: " + urlParams.get("code");
messageElement.textContent = "실패 사유: " + urlParams.get("message");
