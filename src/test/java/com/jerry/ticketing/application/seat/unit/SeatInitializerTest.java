package com.jerry.ticketing.application.seat.unit;

import com.jerry.ticketing.seat.application.manager.SeatManager;
import com.jerry.ticketing.seat.infrastructure.batch.BatchHelper;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SeatInitializerTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private BatchHelper batchHelper;

    @InjectMocks
    private SeatManager seatFactory;

    @BeforeEach
    void setUp() {
        seatRepository.deleteAll();
    }

//    @Test
//    @DisplayName("좌석이 없을 때 초기화하면 좌석을 생성")
//    void shouldInitializeSeatsWhenNoSeatsExist(){
//        // Given
//        when(seatRepository.count()).thenReturn(0L);
//        when(batchSaveHelper.saveIfFull(any(List.class), anyInt(), any(SeatRepository.class))).thenReturn(100);
//
//        // When
//        seatFactory.initializeSeats();
//
//        // Then
//        verify(seatRepository, times(1)).count();
//        verify(seatRepository, atLeastOnce()).saveAll(anyList());
//    }


    @Test
    @DisplayName("좌석이 이미 존재할 때 초기화 매서드를 호출하면 건너뜀")
    void shouldSkipInitializationWhenNoSeatsExist() {
        // Given
        when(seatRepository.count()).thenReturn(1000L);

        // When
        seatFactory.initializeSeats();

        // Then
        verify(seatRepository, times(1)).count();
        verify(seatRepository, never()).saveAll(anyList());
    }

}