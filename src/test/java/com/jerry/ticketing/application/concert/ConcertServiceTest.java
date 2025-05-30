package com.jerry.ticketing.application.concert;

import com.jerry.ticketing.application.seat.ConcertInitializationService;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.domain.concert.ConcertMapper;
import com.jerry.ticketing.dto.CreateConcert;
import com.jerry.ticketing.global.exception.BusinessException;
import com.jerry.ticketing.global.exception.ConcertErrorCode;
import com.jerry.ticketing.repository.concert.ConcertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private ConcertInitializationService concertSeatInitializer;

    @Mock
    private ConcertMapper concertMapper;

    @InjectMocks
    private ConcertService concertService;

    private Concert savedConcert;
    private CreateConcert.Request request;


    @BeforeEach
    void setUp(){

        request = CreateConcert.Request.of(
                "Test-Title", OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES),
                "Test-Venue", 100_000, "Test-Description", 3);

        savedConcert = Concert.createConcert(
                "Test-Title", OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES)
                , "Test-Venue", 100_000, "Test-Description", 3);
    }

    @Test
    @DisplayName("콘서트 생성 시 정상적으로 저장하고 좌석을 초기화한 후 응답을 반환")
    void shouldCreateConcert() {

        // Given
        when(concertMapper.buildConcert(any(CreateConcert.Request.class))).thenReturn(savedConcert);
        when(concertRepository.save(any(Concert.class))).thenReturn(savedConcert);
        doNothing().when(concertSeatInitializer).initializeSectionAndConcertSeats(savedConcert.getId());


        // When
        CreateConcert.Response response = concertService.createConcert(request);


        // Then
        assertThat(response).isNotNull();
        assertThat(response.getDateTime()).isNotNull();
        assertThat(response.getPrice()).isEqualTo(100_000);

        // 메서드 호출 검증
        verify(concertRepository, times(1)).save(any(Concert.class));
        verify(concertSeatInitializer, times(1)).initializeSectionAndConcertSeats(savedConcert.getId());

    }


    @Test
    @DisplayName("콘서스 저상 실패 시 Concert Error 코드 검증")
    void shouldThrowConcertErrorCodeWhenSaveFails(){

        // Given
        when(concertMapper.buildConcert(any(CreateConcert.Request.class))).thenReturn(savedConcert);
        when(concertRepository.save(any(Concert.class)))
                .thenThrow(new BusinessException(ConcertErrorCode.CONCERT_SAVE_FAILED));



        // When & Then
        assertThatThrownBy(() -> concertService.createConcert(request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ConcertErrorCode.CONCERT_SAVE_FAILED);
    }
}