// URL 파라미터 분석
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');
const error = urlParams.get('error');
const type = urlParams.get('type');

// 요소들 가져오기
const loadingIcon = document.getElementById('loadingIcon');
const successIcon = document.getElementById('successIcon');
const errorIcon = document.getElementById('errorIcon');
const statusTitle = document.getElementById('statusTitle');
const statusSubtitle = document.getElementById('statusSubtitle');
const encouragingText = document.getElementById('encouragingText');

function showSuccess() {
    loadingIcon.style.display = 'none';
    successIcon.style.display = 'block';
    statusTitle.textContent = '로그인 성공!';
    statusSubtitle.textContent = '환영합니다! 곧 메인 페이지로 이동합니다.';
    encouragingText.textContent = '성공! 🎉';
    encouragingText.style.color = '#10b981';
}

function showError(errorMessage) {
    loadingIcon.style.display = 'none';
    errorIcon.style.display = 'block';
    statusTitle.textContent = '로그인 실패';
    statusSubtitle.textContent = errorMessage;
    encouragingText.textContent = '다시 시도해주세요 😅';
    encouragingText.style.color = '#ef4444';
}

// 실제 로그인 처리
setTimeout(() => {
    if (token && type === 'google') {
        // JWT 토큰을 로컬 스토리지에 저장
        localStorage.setItem('accessToken', token);
        showSuccess();

        setTimeout(() => {
            window.location.href = '/';
        }, 2000);

    } else if (error) {
        showError('Google 로그인 실패: ' + error);

        setTimeout(() => {
            window.location.href = '/';
        }, 3000);

    } else {
        showError('알 수 없는 오류가 발생했습니다.');

        setTimeout(() => {
            window.location.href = '/';
        }, 3000);
    }
}, 1500); // 1.5초 로딩 시간
