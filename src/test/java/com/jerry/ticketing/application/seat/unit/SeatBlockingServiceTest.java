package com.jerry.ticketing.application.seat.unit;

import com.jerry.ticketing.seat.application.ConcertSeatBlockingService;
import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.vo.ConcertSeats;
import com.jerry.ticketing.seat.application.dto.web.ConcertSeatBlockDto;
import com.jerry.ticketing.global.validation.ConcertSeatBlockValidator;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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

    @Mock
    private ConcertSeatBlockValidator concertSeatBlockValidator;


    @InjectMocks
    private ConcertSeatBlockingService seatBlockingService;

    private List<ConcertSeat> concertSeats;
    private ConcertSeatBlockDto.Request request;

    @BeforeEach
    void setup() {

        // 테스트 데이터 설정
        this.request = new ConcertSeatBlockDto.Request(1L, Arrays.asList(1L, 2L), 100L);


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
        when(concertSeatRepository.findByConcertIdAndIdIn(request.getConcertId(), request.getConcertSeatIds()))
                .thenReturn(concertSeats);

        ConcertSeats BlockConcertSeats = new ConcertSeats(concertSeats);
        doNothing().when(concertSeatBlockValidator).validator(BlockConcertSeats, request.getConcertSeatIds());


        // When
        List<ConcertSeat> result = seatBlockingService.blockSeats(request);

        // Then
        assertThat(result).isEqualTo(concertSeats);


    }

}