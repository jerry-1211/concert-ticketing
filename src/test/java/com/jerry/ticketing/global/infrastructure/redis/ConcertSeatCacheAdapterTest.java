package com.jerry.ticketing.global.infrastructure.redis;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConcertSeatCacheAdapterTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private ConcertSeatCacheAdapter cacheAdapter;


    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("콘서트 좌석 점유 정보가 캐시에 저장된다.")
    void concertSeatOccupancyInfoShouldBeSavedInCache() {
        // given
        ConcertSeat concertSeat = ConcertSeat.of(1L, 1L, 1L, 1_000);

        // when
        String key = cacheAdapter.cacheConcertSeatOccupancy(concertSeat);

        // then
        verify(valueOperations).set(eq(key), eq(concertSeat.getBlockedBy()), eq(Duration.ofHours(1)));
        assertThat(key).contains("concert:" + concertSeat.getConcertId() + ":seat:" + concertSeat.getSeatId());
    }


    @Test
    @DisplayName("모든 콘서트 좌석들의 점유 정보가 캐시에 저장된다.")
    void allConcertSeatOccupancyInfoShouldBeSavedInCache() {
        // given
        ConcertSeat concertSeat1 = ConcertSeat.of(1L, 1L, 1L, 1_000);
        ConcertSeat concertSeat2 = ConcertSeat.of(1L, 2L, 1L, 1_000);
        ConcertSeat concertSeat3 = ConcertSeat.of(1L, 3L, 1L, 1_000);

        // when
        List<String> keys = cacheAdapter.cacheConcertSeatOccupancies(List.of(concertSeat1, concertSeat2, concertSeat3));

        // then
        verify(valueOperations).set(eq(keys.get(0)), eq(concertSeat1.getBlockedBy()), eq(Duration.ofHours(1)));
        verify(valueOperations).set(eq(keys.get(1)), eq(concertSeat1.getBlockedBy()), eq(Duration.ofHours(1)));
        verify(valueOperations).set(eq(keys.get(2)), eq(concertSeat1.getBlockedBy()), eq(Duration.ofHours(1)));

        assertThat(keys.get(0)).contains("concert:" + concertSeat1.getConcertId() + ":seat:" + concertSeat1.getSeatId());
        assertThat(keys.get(1)).contains("concert:" + concertSeat2.getConcertId() + ":seat:" + concertSeat2.getSeatId());
        assertThat(keys.get(2)).contains("concert:" + concertSeat3.getConcertId() + ":seat:" + concertSeat3.getSeatId());
    }


    @Test
    @DisplayName("모든 콘서트 좌석들 중 하나도 캐쉬에 저장되지 않으면 True를 반환한다.")
    void returnsTrueWhenNoConcertSeatsAreCached() {
        // given
        Long concertId = 1L;
        List<Long> seatIds = List.of(1L, 2L, 3L);

        String key1 = "concert:" + concertId + ":seat:" + seatIds.get(0);
        String key2 = "concert:" + concertId + ":seat:" + seatIds.get(1);
        String key3 = "concert:" + concertId + ":seat:" + seatIds.get(2);

        List<String> keys = List.of(key1, key2, key3);

        // when
        when(redisTemplate.opsForValue().multiGet(keys))
                .thenReturn(Arrays.asList(null, null, null));

        // then
        assertThat(cacheAdapter.areAllConcertSeatsNotCached(concertId, seatIds)).isTrue();
    }


    @Test
    @DisplayName("모든 콘서트 좌석들 중 하나도 캐쉬에 저장되어 있으면 False를 반환한다.")
    void returnsFalseWhenAnyConcertSeatIsCached() {
        // given
        Long concertId = 1L;
        List<Long> seatIds = List.of(1L, 2L, 3L);

        String key1 = "concert:" + concertId + ":seat:" + seatIds.get(0);
        String key2 = "concert:" + concertId + ":seat:" + seatIds.get(1);
        String key3 = "concert:" + concertId + ":seat:" + seatIds.get(2);

        List<String> keys = List.of(key1, key2, key3);

        // when
        when(redisTemplate.opsForValue().multiGet(keys))
                .thenReturn(Arrays.asList(null, 1L, null));


        // then
        assertThat(cacheAdapter.areAllConcertSeatsNotCached(concertId, seatIds)).isFalse();
    }


}