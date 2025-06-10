document.addEventListener('DOMContentLoaded', function () {
    // 현재 날짜와 시간을 기본값으로 설정
    const dateTimeInput = document.getElementById('dateTime');
    if (!dateTimeInput.value) {
        const now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        dateTimeInput.value = now.toISOString().slice(0, 16);
    }

    const form = document.getElementById('concertForm');
    const submitBtn = document.getElementById('submitBtn');

    // 알림 메시지 표시 함수
    function showAlert(type, message) {
        const alertContainer = document.getElementById('alert-container');
        const alert = document.createElement('div');
        alert.className = `alert alert-${type} alert-dismissible fade show`;
        alert.innerHTML = `
      ${message}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
        alertContainer.innerHTML = '';
        alertContainer.appendChild(alert);
    }

    // 유효성 검증 오류 처리 함수
    function handleValidationErrors(errorData) {
        document.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

        if (errorData.errors) {
            errorData.errors.forEach(error => {
                const field = document.querySelector(`[name="${error.field}"]`);
                if (field) {
                    field.classList.add('is-invalid');
                    const feedback = field.closest('.mb-3, .mb-4').querySelector('.invalid-feedback');
                    if (feedback) {
                        feedback.textContent = error.defaultMessage || error.message;
                    }
                }
            });
        } else {
            showAlert('danger', errorData.message || '입력값을 확인해주세요.');
        }
    }

    form.addEventListener('submit', async function (e) {
        e.preventDefault();
        console.log('폼 제출 방지됨'); // 디버깅용

        // 로딩 상태 시작
        submitBtn.disabled = true;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin me-2"></i>등록 중...';
        form.classList.add('loading');

        // 폼 데이터 수집
        const formData = new FormData(form);

        const data = {
            title: formData.get('title'),
            dateTime: formData.get('dateTime') ? new Date(formData.get('dateTime')).toISOString() : null,
            venue: formData.get('venue'),
            price: parseInt(formData.get('price')) || 0,
            maxTicketsPerUser: parseInt(formData.get('maxTicketsPerUser')) || 0,
            description: formData.get('description') || ""
        };

        console.log('전송할 데이터:', data); // 디버깅용

        try {
            const response = await fetch('/api/concerts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data)
            });

            console.log('응답 상태:', response.status); // 디버깅용

            if (response.ok) {
                const result = await response.json();
                showAlert('success', '콘서트가 성공적으로 등록되었습니다!');
                setTimeout(() => {
                    window.location.href = '/concerts';
                }, 1500);
            } else {
                try {
                    const errorData = await response.json();
                    handleValidationErrors(errorData);
                } catch {
                    showAlert('danger', '등록 중 오류가 발생했습니다.');
                }
            }
        } catch (error) {
            console.error('네트워크 오류:', error);
            showAlert('danger', '서버 연결 오류가 발생했습니다.');
        } finally {
            // 로딩 상태 해제
            submitBtn.disabled = false;
            submitBtn.innerHTML = '<i class="fas fa-save me-2"></i>콘서트 등록';
            form.classList.remove('loading');
        }
    });
});
