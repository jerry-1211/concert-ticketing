package com.jerry.ticketing.global.infrastructure.redis;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConcertSeatCacheService {
    private final RedisTemplate<String, Object> redisTemplate;


    public boolean AllConcertSeatsNotCached(Long concertId, List<Long> concertSeatIds) {

        List<String> keys = concertSeatIds.stream()
                .map(concertSeatId -> buildKey(concertId, concertSeatId))
                .toList();

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);

        return values.stream().allMatch(Objects::isNull);
    }

    private String buildKey(Long concertId, Long concertSeatId) {
        return "concert:" + concertId + ":concertSeat:" + concertSeatId;
    }


//    public ConcertSeats createConcertSeatsFromCache(Long concertId, List<Long> ConcertSeatIds) {
//        List<ConcertSeat> concertSeats = new ArrayList<>();
//        for (Long concertSeatId : ConcertSeatIds) {
//            String key = String.format(CONCERT_SEAT_KEY, concertId, concertSeatId);
//            ConcertSeat concertSeat = (ConcertSeat) redisTemplate.opsForValue().get(key);
//            concertSeats.add(concertSeat);
//        }
//        return ConcertSeats.from(concertSeats);
//    }


    public void saveToCache(List<ConcertSeat> concertSeats) {
        List<String> keys = concertSeats.stream()
                .map(concertSeat -> buildKey(concertSeat.getConcertId(), concertSeat.getId()))
                .toList();

        for (ConcertSeat concertSeat : concertSeats) {
            String key = buildKey(concertSeat.getConcertId(), concertSeat.getId());
            redisTemplate.opsForValue().set(key, concertSeat.getBlockedBy(), Duration.ofHours(1));
        }
    }

}
