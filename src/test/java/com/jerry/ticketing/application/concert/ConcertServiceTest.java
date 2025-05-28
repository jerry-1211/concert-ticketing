package com.jerry.ticketing.application.concert;

import com.jerry.ticketing.application.seat.ConcertSeatInitializer;
import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.dto.CreateConcert;
import com.jerry.ticketing.exception.BusinessException;
import com.jerry.ticketing.exception.ConcertErrorCode;
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
    private ConcertSeatInitializer concertSeatInitializer;

    @InjectMocks
    private ConcertService concertService;

    private Concert savedConcert;
    private CreateConcert.Request request;


    @BeforeEach
    void setUp(){
        savedConcert = Concert.builder()
                .id(1L)
                .title("Test-Title")
                .dateTime(OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES))
                .venue("Test-Venue")
                .price(100_000)
                .description("Test-Description")
                .maxTicketsPerUser(3)
                .build();

        request= CreateConcert.Request.builder()
                    .title("Test-Title")
                    .dateTime(OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES))
                    .venue("Test-Venue")
                    .price(100_000)
                    .description("Test-Description")
                    .maxTicketsPerUser(3)
                    .build();
    }

    @Test
    @DisplayName("콘서트 생성 시 정상적으로 저장하고 좌석을 초기화한 후 응답을 반환")
    void shouldCreateConcert() {

        // Given
        when(concertRepository.save(any(Concert.class))).thenReturn(savedConcert);
        doNothing().when(concertSeatInitializer).initializeSectionAndConcertSeats(savedConcert.getId());


        // When
        CreateConcert.Response response = concertService.createConcert(request);


        // Then
        assertThat(response).isNotNull();
        assertThat(response.getDateTime()).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getPrice()).isEqualTo(100_000);

        // 메서드 호출 검증
        verify(concertRepository, times(1)).save(any(Concert.class));
        verify(concertSeatInitializer, times(1)).initializeSectionAndConcertSeats(anyLong());

    }


    @Test
    @DisplayName("콘서스 저상 실패 시 Concert Error 코드 검증")
    void shouldThrowConcertErrorCodeWhenSaveFails(){

        // Given
        when(concertRepository.save(any(Concert.class)))
                .thenThrow(new RuntimeException("콘서트 저장 실패"));


        // When & Then
        assertThatThrownBy(() -> concertService.createConcert(request))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ConcertErrorCode.CONCERT_SAVE_FAILED);
    }
}