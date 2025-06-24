// 페이지 로드 시 기존 로그인 체크
window.addEventListener('DOMContentLoaded', function () {
    checkExistingLogin();
    setupGoogleLogin();
    checkForErrors();
});

function checkExistingLogin() {
    const token = localStorage.getItem('accessToken');
    if (token) {
        // 토큰이 있으면 유효성 검증
        validateToken(token);
    } else {
        // 토큰이 없으면 로그인 버튼 표시
        showLoginButton();
    }
}

async function validateToken(token) {
    try {
        const response = await fetch('/api/auth/validate', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            // 토큰이 유효하면 테스트 버튼 표시
            showTestButton();
        } else {
            // 토큰이 무효하면 제거하고 로그인 버튼 표시
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            showLoginButton();
        }
    } catch (error) {
        // 오류 발생시 토큰 제거하고 로그인 버튼 표시
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        showLoginButton();
    }
}

function setupGoogleLogin() {
    const googleBtn = document.getElementById('googleLoginBtn');
    const loadingMessage = document.getElementById('loadingMessage');

    if (googleBtn) {
        googleBtn.addEventListener('click', function (e) {
            showLoading();
        });
    }
}

function showLoading() {
    const googleBtn = document.getElementById('googleLoginBtn');
    const loadingMessage = document.getElementById('loadingMessage');

    if (googleBtn) googleBtn.style.opacity = '0.6';
    if (googleBtn) googleBtn.style.pointerEvents = 'none';
    if (loadingMessage) loadingMessage.style.display = 'block';
}

function showLoginButton() {
    const googleBtn = document.getElementById('googleLoginBtn');
    const testBtn = document.getElementById('testBtn');
    const loginSubtitle = document.getElementById('login-subtitle');
    const mypageBtn = document.getElementById('mypageBtn');

    if (googleBtn) googleBtn.style.display = 'flex';
    if (loginSubtitle) loginSubtitle.style.display = 'flex';
    if (testBtn) testBtn.style.display = 'none';
    if (mypageBtn) mypageBtn.style.display = 'none';

}

function showTestButton() {
    const googleBtn = document.getElementById('googleLoginBtn');
    const testBtn = document.getElementById('testBtn');
    const loginSubtitle = document.getElementById('login-subtitle');
    const mypageBtn = document.getElementById('mypageBtn');

    if (googleBtn) googleBtn.style.display = 'none';
    if (loginSubtitle) loginSubtitle.style.display = 'none';
    if (testBtn) testBtn.style.display = 'flex';
    if (mypageBtn) mypageBtn.style.display = 'flex';
}

function goToTest() {
    window.location.href = '/concerts';
}

function goToMyPage() {
    window.location.href = '/mypage';
}

function checkForErrors() {
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');

    if (error) {
        const errorMessages = {
            'access_denied': '로그인이 취소되었습니다.',
            'invalid_request': '잘못된 요청입니다.',
            'server_error': '서버 오류가 발생했습니다.',
            'LOGIN_FAILED': 'Google 로그인에 실패했습니다.'
        };

        const message = errorMessages[error] || '로그인 중 오류가 발생했습니다.';
        alert(message);

        window.history.replaceState({}, document.title, window.location.pathname);
    }
}

window.addEventListener('focus', function () {
    const token = localStorage.getItem('accessToken');
    if (token) {
        validateToken(token);
    }
});
