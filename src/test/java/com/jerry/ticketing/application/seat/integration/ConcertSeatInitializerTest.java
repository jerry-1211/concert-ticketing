package com.jerry.ticketing.application.seat.integration;

import com.jerry.ticketing.seat.application.ConcertInitializationService;
import com.jerry.ticketing.seat.infrastructure.factory.SectionFactory;
import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.Section;
import com.jerry.ticketing.concert.infrastructure.repository.ConcertRepository;
import com.jerry.ticketing.seat.infrastructure.repository.ConcertSeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SeatRepository;
import com.jerry.ticketing.seat.infrastructure.repository.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConcertSeatInitializerTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private SectionFactory sectionFactory;

    @Mock
    private ConcertSeatRepository concertSeatRepository;

    @InjectMocks
    private ConcertInitializationService concertSeatInitializer;

    private Section mockSection;

    @BeforeEach
    void setup(){
        // 기본 Mock 설정
        Concert mockConcert = mock(Concert.class);
        mockSection = mock(Section.class);
        Seat mockSeat = mock(Seat.class);

        when(concertRepository.findById(anyLong())).thenReturn(Optional.of(mockConcert));

        when(seatRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockSeat));

        when(sectionRepository.save(any(Section.class))).thenReturn(mockSection);

    }


    @Test
    @DisplayName("Section에 매팽된 Concert가 없을 때 Section & ConcertSeat 초기화 메서드 호출")
    void shouldInitializeSectionAndConcertSeatsWhenConcertNotMapped(){

//        // When
//       concertSeatInitializer.initializeSectionAndConcertSeats(1L);
//        when(sectionFactory.create(1L)).thenReturn(mockSection);
//
//        // Then
//        verify(concertSeatRepository,times(1)).findByConcertId(anyLong());

    }

//    @Test
//    @DisplayName("이미 Section에 매핑된 Concert가 있는 경우 초기화를 건너뜀")
//    void shouldSkipInitializeSectionAndConcertSeatsWhenConcertMapped(){
//        // Given
//        when(sectionRepository.existsByConcertId(anyLong())).thenReturn(true);
//
//        // When
//        concertSeatInitializer.initializeSectionAndConcertSeats(1L);
//
//        // Then
//        verify(sectionRepository,times(1)).existsByConcertId(anyLong());
//        verify(sectionRepository, never()).save(any(Section.class));
//        verify(concertSeatRepository, never()).saveAll(anyList());
//    }
//
//    @Test
//    @DisplayName("Section이 이미 존재할 때 기존 것을 반환 - createSectionIfNotExists 메서드 검증" )
//    void shouldReturnExistingSectionWhenExits(){
//
//        // Given
//        when(sectionRepository.findByConcertId(anyLong()))
//                .thenReturn(Optional.of(mockSection));
//
//        // When
//        concertSeatInitializer.initializeSectionAndConcertSeats(1L);
//
//        // Then
//        verify(sectionRepository, atLeastOnce()).findByConcertId(anyLong());
//        verify(sectionRepository,never()).save(any(Section.class));
//    }
//
//
//    @Test
//    @DisplayName("ConcertSeat 이미 존재할 때 기존 것을 반환 - createConcertSeats 메서드 검증")
//    void shouldReturnExistingConcertSeatWhenExits(){
//        ConcertSeat mockConcertSeat = mock(ConcertSeat.class);
//
//        // Given
//        when(sectionRepository.findByConcertId(anyLong())).thenReturn(Optional.of(mockSection));
//        when(concertSeatRepository.findByConcertId(anyLong())).thenReturn(List.of(mockConcertSeat));
//
//        // When
//        concertSeatInitializer.initializeSectionAndConcertSeats(1L);
//
//        // Then
//        verify(sectionRepository, atLeastOnce()).findByConcertId(anyLong());
//        verify(concertSeatRepository,never()).saveAll(anyList());
//
//
//    }

}