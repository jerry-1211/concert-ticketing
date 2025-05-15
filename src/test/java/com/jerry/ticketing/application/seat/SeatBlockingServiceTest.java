package com.jerry.ticketing.application.seat;

import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.domain.seat.enums.ConcertSeatStatus;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.SeatErrorCode;
import com.jerry.ticketing.repository.seat.ConcertSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeatBlockingServiceTest {

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private SeatBlockingService seatBlockingService;

    private List<ConcertSeat> concertSeats;
    private Long memberId;
    private List<Long> seatIds;
    private Long concertId;


    @BeforeEach
    void setup() {

        // 테스트 데이터 설정
        concertId = 1L;
        seatIds = Arrays.asList(1L, 2L);
        memberId = 100L;


        // Mock ConcertSeat 객체 생성
        ConcertSeat concertSeat1 = mock(ConcertSeat.class);
        ConcertSeat concertSeat2 = mock(ConcertSeat.class);

        when(concertSeat1.isAvailable()).thenReturn(true);
        when(concertSeat2.isAvailable()).thenReturn(true);

        concertSeats = Arrays.asList(concertSeat1, concertSeat2);
    }

    @Test
    @DisplayName("좌석 선택 및 일시적 선점 상태 확인 테스트")
    void blockSeat_Success() {

        // Given
        when(concertSeatRepository.findByConcertIdAndSeatIdIn(concertId, seatIds))
                .thenReturn(concertSeats);

        when(concertSeatRepository.saveAll(concertSeats))
                .thenReturn(concertSeats);

        // When
        List<ConcertSeat> result = seatBlockingService.blockSeats(concertId, seatIds, memberId);

        // Then
        assertThat(result).isEqualTo(concertSeats);

        for (ConcertSeat concertSeat : concertSeats) {
            verify(concertSeat).setStatus(ConcertSeatStatus.BLOCKED);
            verify(concertSeat).setBlockedBy(memberId);
            verify(concertSeat).setBlockedAt(any(LocalDateTime.class));
            verify(concertSeat).setBlockedExpireAt(any(LocalDateTime.class));
        }
    }


    @Test
    @DisplayName("요청 좌석 중 일부 찾을 수 없을 때 Seat Error 코드 검증")
    void block_SeatNotFound() {
        // Given
        when(concertSeatRepository.findByConcertIdAndSeatIdIn(concertId, seatIds))
                .thenReturn(List.of(concertSeats.get(0)));

        // When & Then
        assertThatThrownBy(() -> seatBlockingService.blockSeats(concertId, seatIds, memberId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", SeatErrorCode.SEAT_NOT_FOUND);

                //hasFieldOrPropertyWithValue("errorCode.message", "요청 좌석 중 일부를 찾을 수 없습니다.");  -> 이렇게 해도 테스트 케이스 통과

        verify(concertSeatRepository, never()).saveAll(any());
    }

    @Test
    @DisplayName("이미 선점된 좌석이 포함되어 있을 경우 Seat Error 코드 검증")
    void block_SeatAlreadyBlocked() {
        // Given
        when(concertSeatRepository.findByConcertIdAndSeatIdIn(concertId, seatIds))
                .thenReturn(concertSeats);

        // When
        when(concertSeats.get(0).isAvailable()).thenReturn(false);


        // Then
        assertThatThrownBy(() -> seatBlockingService.blockSeats(concertId, seatIds, memberId))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", SeatErrorCode.SEAT_ALREADY_BLOCKED);


        verify(concertSeats.get(0),never()).setStatus(any());
        verify(concertSeatRepository, never()).saveAll(any());
    }


    @Test
    @DisplayName("만료된 좌석 선점 자동 해제 확인 테스트")
    void releaseExpiredBlockedSeats_Success(){
        // Given
        LocalDateTime now = LocalDateTime.now();

        ConcertSeat expiredConcertSeat1 = mock(ConcertSeat.class);
        ConcertSeat expiredConcertSeat2 = mock(ConcertSeat.class);
        List<ConcertSeat> expiredSeats = Arrays.asList(expiredConcertSeat1, expiredConcertSeat2);

        when(concertSeatRepository.findByBlockedExpireAtBeforeAndStatus(
                any(LocalDateTime.class), eq(ConcertSeatStatus.BLOCKED)))
                .thenReturn(expiredSeats);

        when(concertSeatRepository.saveAll(expiredSeats)).thenReturn(expiredSeats);

        // When
        seatBlockingService.releaseExpiredBlockedSeats();

        //Then
        verify(concertSeatRepository).findByBlockedExpireAtBeforeAndStatus(
                any(LocalDateTime.class), eq(ConcertSeatStatus.BLOCKED));

        for (ConcertSeat expiredSeat : expiredSeats) {
            verify(expiredSeat).setStatus(ConcertSeatStatus.AVAILABLE);
            verify(expiredSeat).setBlockedBy(null);
            verify(expiredSeat).setBlockedAt(null);
            verify(expiredSeat).setBlockedExpireAt(null);
        }

        verify(concertSeatRepository).saveAll(expiredSeats);
    }

}