// URL에서 concertId 가져오기
const urlParams = new URLSearchParams(window.location.search);
const concertId = urlParams.get('concertId');

let selectedZone = '';
let selectedRow = '';
let selectedSeats = [];

// 페이지 로드 시 Zone 버튼들 생성
document.addEventListener('DOMContentLoaded', function () {
    loadZones();

    // 예약 버튼 이벤트 리스너 추가
    document.getElementById('reserveBtn').addEventListener('click', reserveSeats);
});

async function loadZones() {
    try {
        const response = await fetch(`/seats/api/zones?concertId=${concertId}`);
        const zones = await response.json();

        const zoneButtons = document.getElementById('zoneButtons');
        zoneButtons.innerHTML = '';

        zones.forEach(zone => {
            const button = document.createElement('button');
            button.className = 'btn';
            button.textContent = zone;
            button.onclick = () => selectZone(zone);
            zoneButtons.appendChild(button);
        });
    } catch (error) {
        console.error('Zone 로드 실패:', error);
    }
}

async function selectZone(zone) {
    selectedZone = zone;
    selectedRow = '';
    selectedSeats = [];

    // Zone 버튼 활성화 표시
    document.querySelectorAll('.zone-buttons .btn').forEach(btn => {
        btn.classList.toggle('active', btn.textContent === zone);
    });

    // Row 섹션 표시 및 로드
    document.getElementById('rowSection').style.display = 'block';
    document.getElementById('seatSection').style.display = 'none';
    document.getElementById('remainingSeats').style.display = 'none';
    updateSelectedSeats();

    try {
        const response = await fetch(`/seats/api/rows?zone=${zone}`);
        const rows = await response.json();

        const rowButtons = document.getElementById('rowButtons');
        rowButtons.innerHTML = '';

        rows.forEach(row => {
            const button = document.createElement('button');
            button.className = 'btn';
            button.textContent = row;
            button.onclick = () => selectRow(row);
            rowButtons.appendChild(button);
        });
    } catch (error) {
        console.error('Row 로드 실패:', error);
    }
}

async function selectRow(row) {
    selectedRow = row;
    selectedSeats = [];

    // Row 버튼 활성화 표시
    document.querySelectorAll('.row-buttons .btn').forEach(btn => {
        btn.classList.toggle('active', btn.textContent === row);
    });

    // 좌석 섹션 표시 및 로드
    document.getElementById('seatSection').style.display = 'block';
    updateSelectedSeats();

    try {
        const response = await fetch(`/seats/api/seats?concertId=${concertId}&zone=${selectedZone}&row=${row}`);
        const seats = await response.json();

        displaySeats(seats);
    } catch (error) {
        console.error('좌석 로드 실패:', error);
    }
}

function displaySeats(seats) {
    const seatGrid = document.getElementById('seatGrid');
    const seatInfo = document.getElementById('seatInfo');
    const seatSection = document.getElementById('seatSection');


    seatGrid.innerHTML = '';

    if (seats.length > 0) {
        const seatType = seats[0].seatType.toLowerCase();
        const totalAmount = seats[0].totalAmount;
        const remainingSeats = seats[0].remainingSeats;
        const zone = seats[0].zone;

        seatInfo.innerHTML = `<p>${selectedZone}-${selectedRow} (${seatType.toUpperCase()}) - ${totalAmount}원</p>`;
        document.getElementById('remainingSeats').innerHTML = `${zone} Zone - ${remainingSeats}좌석 남음 🔥`;
        document.getElementById('remainingSeats').style.display = 'block';


        seats.forEach(seat => {
            const seatElement = document.createElement('div');
            let className = `seat ${seatType}`;

            // status가 AVAILABLE이 아니면 추가 클래스 적용
            if (seat.status !== 'AVAILABLE') {
                className += " notable";
            }

            seatElement.className = className;
            seatElement.textContent = seat.seatId;
            seatElement.onclick = () => toggleSeat(seat);
            seatElement.dataset.concertSeatId = seat.concertSeatId;
            seatGrid.appendChild(seatElement);
        });
    }
}


function toggleSeat(seat) {
    const seatElement = document.querySelector(`[data-concert-seat-id="${seat.concertSeatId}"]`);
    const isSelected = selectedSeats.find(s => s.concertSeatId === seat.concertSeatId);

    if (isSelected) {
        // 선택 해제
        selectedSeats = selectedSeats.filter(s => s.concertSeatId !== seat.concertSeatId);
        seatElement.classList.remove('selected');
    } else {
        // 최대 선택 가능 수량 체크 (추가된 부분)
        if (selectedSeats.length >= MAX_TICKETS_PER_USER) {
            alert(`최대 ${MAX_TICKETS_PER_USER}매까지만 선택 가능합니다.`);
            return;
        }

        // 선택
        selectedSeats.push(seat);
        seatElement.classList.add('selected');
    }

    updateSelectedSeats();
}


function updateSelectedSeats() {
    const selectedSeatsList = document.getElementById('selectedSeatsList');
    const reserveBtn = document.getElementById('reserveBtn');

    document.getElementById('selectedCount').textContent = selectedSeats.length;


    if (selectedSeats.length === 0) {
        selectedSeatsList.innerHTML = '<p>선택된 좌석이 없습니다.</p>';
        reserveBtn.disabled = true;
    } else {
        const seatNames = selectedSeats.map(seat => `${seat.zone}-${seat.row}-${seat.seatId}`).join(', ');
        const totalAmount = selectedSeats.reduce((sum, seat) => sum + seat.totalAmount, 0);
        selectedSeatsList.innerHTML = `
                <p>선택된 좌석: ${seatNames}</p>
                <p>총 가격: ${totalAmount.toLocaleString()}원</p>
            `;
        reserveBtn.disabled = false;
    }
}

async function reserveSeats() {
    if (selectedSeats.length === 0) {
        alert('좌석을 선택해주세요.');
        return;
    }

    const request = {
        concertId: concertId,
        concertSeatIds: selectedSeats.map(seat => seat.concertSeatId),
        memberId: 1 // Todo: 실제로는 로그인된 사용자 ID를 사용
    };

    try {
        const response = await fetch('/api/seats/blocks', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        });

        if (response.ok) {
            const result = await response.json();

            // 예약된 좌석들을 화면에서 제거
            selectedSeats.forEach(seat => {
                const seatElement = document.querySelector(`[data-concert-seat-id="${seat.concertSeatId}"]`);
                if (seatElement) {
                    seatElement.remove();
                }
            });

            // 결제 시스템 호출
            await window.PaymentSystem.reservationConcertSeat(result, selectedSeats, concertId);
            selectedSeats = [];
            updateSelectedSeats();
        } else {
            const error = await response.text();
            alert('예약 실패: ' + error);
        }
    } catch (error) {
        console.error('예약 요청 실패:', error);
        alert('예약 요청 중 오류가 발생했습니다.');
    }
}
