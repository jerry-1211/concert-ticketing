// 페이지 로드 시 마이페이지 정보 조회
window.addEventListener('DOMContentLoaded', loadMyPage);

async function loadMyPage() {
    const token = localStorage.getItem('accessToken');
    if (!token) {
        alert('로그인이 필요합니다.');
        window.location.href = '/login';
        return;
    }

    try {
        const response = await fetch('/api/mypage', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            displayProfile(data);
            fillUpdateForm(data);
        } else {
            alert('프로필 정보를 불러올 수 없습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    }
}

function displayProfile(profile) {
    const profileHeader = document.getElementById('profileHeader');
    profileHeader.innerHTML = `
        <img src="${profile.profileImage || '/default-profile.png'}" alt="프로필" class="profile-img">
        <div class="profile-text">
            <h2>${profile.name}</h2>
            <p>${profile.email}</p>
        </div>
    `;
}

function fillUpdateForm(profile) {
    document.getElementById('name').value = profile.name || '';
    document.getElementById('phoneNumber').value = profile.phoneNumber || '';
    document.getElementById('profileImage').value = profile.profileImage || '';
}

function toggleEdit() {
    const editSection = document.getElementById('editSection');
    if (editSection.style.display === 'none' || editSection.style.display === '') {
        editSection.style.display = 'block';
    } else {
        editSection.style.display = 'none';
    }
}

// 프로필 수정 폼 제출
document.getElementById('updateForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('accessToken');
    const formData = new FormData(e.target);
    const updateData = {
        name: formData.get('name'),
        phoneNumber: formData.get('phoneNumber'),
        profileImage: formData.get('profileImage')
    };

    try {
        const response = await fetch('/api/mypage/profile', {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateData)
        });

        if (response.ok) {
            const updatedProfile = await response.json();
            alert('프로필이 수정되었습니다.');
            displayProfile(updatedProfile);
            toggleEdit();
        } else {
            alert('프로필 수정에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    }
});

async function logout() {
    try {
        await fetch('/api/auth/logout', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
                'Content-Type': 'application/json'
            }
        });

        localStorage.clear();
        sessionStorage.clear();
        window.location.replace('/');

    } catch (error) {
        console.error('로그아웃 실패:', error);
        localStorage.clear();
        sessionStorage.clear();
        window.location.replace('/');
    }
}
