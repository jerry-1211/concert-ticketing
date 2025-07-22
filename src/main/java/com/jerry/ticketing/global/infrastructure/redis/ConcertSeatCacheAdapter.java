package com.jerry.ticketing.global.infrastructure.redis;


import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.port.ConcertSeatCache;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConcertSeatCacheAdapter implements ConcertSeatCache {

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean areAllConcertSeatsNotCached(Long concertId, List<Long> seatIds) {

        List<String> keys = seatIds.stream()
                .map(seatId -> buildKey(concertId, seatId))
                .toList();

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);

        return values.stream().allMatch(Objects::isNull);
    }


    @Override
    public List<String> cacheConcertSeatOccupancies(List<ConcertSeat> concertSeats) {
        List<String> keys = new ArrayList<>();
        for (ConcertSeat concertSeat : concertSeats) {
            keys.add(cacheConcertSeatOccupancy(concertSeat));
        }
        return keys;
    }


    @Override
    public String cacheConcertSeatOccupancy(ConcertSeat concertSeat) {
        String key = buildKey(concertSeat.getConcertId(), concertSeat.getSeatId());
        redisTemplate.opsForValue().set(key, concertSeat.getBlockedBy(), Duration.ofHours(1));
        return key;
    }


    private String buildKey(Long concertId, Long seatId) {
        return "concert:" + concertId + ":seat:" + seatId;
    }


}
