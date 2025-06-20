window.addEventListener('DOMContentLoaded', loadReservations);

async function loadReservations() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login';
        return;
    }

    try {
        const response = await fetch('/api/mypage/reservation', {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });

        hideLoadingMessage();

        if (response.ok) {
            const reservations = await response.json();
            displayReservations(reservations);
        } else if (response.status === 401) {
            alert('로그인이 만료되었습니다. 다시 로그인해주세요.');
            localStorage.removeItem('accessToken');
            window.location.href = '/login';
        } else {
            showError();
        }
    } catch (error) {
        console.error('Error:', error);
        hideLoadingMessage();
        showError();
    }
}

function displayReservations(reservations) {
    const reservationGrid = document.getElementById('reservationGrid');
    const noReservations = document.getElementById('noReservations');

    if (reservations.length === 0) {
        reservationGrid.style.display = 'none';
        noReservations.style.display = 'block';
        return;
    }

    const reservationHtml = reservations.map(reservation => `
            <div class="reservation-card">
                <div class="concert-title">${reservation.title}</div>

                <div class="concert-info">
                    <div style="margin-bottom: 5px;">
                        <span class="icon">📍</span>${reservation.venue}
                    </div>
                    <div>
                        <span class="icon">📅</span>${formatDateTime(reservation.dateTime)}
                    </div>
                </div>

                <div class="reservation-details">
                    <div class="detail-row">
                        <span class="detail-label">주문번호</span>
                        <span class="detail-value">${reservation.orderId}</span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">결제금액</span>
                        <span class="detail-value amount">${reservation.totalAmount.toLocaleString()}원</span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">예약일시</span>
                        <span class="detail-value">${formatDateTime(reservation.createdAt)}</span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">상태</span>
                        <span class="status status-${reservation.status}">
                            ${getStatusText(reservation.status)}
                        </span>
                    </div>
                </div>
            </div>
        `).join('');

    reservationGrid.innerHTML = reservationHtml;
}

function formatDateTime(dateTimeString) {
    if (!dateTimeString) return '정보 없음';

    const date = new Date(dateTimeString);
    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        weekday: 'short'
    };
    return date.toLocaleDateString('ko-KR', options);
}

function getStatusText(status) {
    const statusMap = {
        'CONFIRMED': '예약확정',
        'PENDING': '결제대기',
        'CANCELLED': '예약취소',
        'EXPIRED': '예약만료'
    };
    return statusMap[status] || status;
}

function hideLoadingMessage() {
    document.getElementById('loadingMessage').style.display = 'none';
}

function showError() {
    document.getElementById('errorMessage').style.display = 'block';
}
