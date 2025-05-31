-- Concert 데이터
INSERT INTO concert (title, date_time, venue, price, description, max_tickets_per_user)
VALUES ('아이유 콘서트 2025', '2025-06-15 19:00:00', '올림픽공원 체조경기장', 120000, '아이유의 특별한 콘서트입니다.', 3);

-- Member 데이터
INSERT INTO member (name, email, password, phone_number, city, street)
VALUES ('김토스', 'kimtoss@example.com', 'password123', '010-1234-5678', '서울시 강남구', '테헤란로 123');

-- Reservation 데이터
INSERT INTO reservation (member_id, concert_id, total_price, reservation_status, created_at, expires_at, amount)
VALUES (1, 1, 50000, 'PENDING', '2025-05-27', '2025-05-29', 2);