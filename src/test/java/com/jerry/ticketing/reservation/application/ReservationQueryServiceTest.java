package com.jerry.ticketing.reservation.application;

import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationQueryServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Reservation mockReservation;

    @InjectMocks
    private ReservationQueryService reservationQueryService;


    @Test
    @DisplayName("reservation Id로 reservation 객체를 가져오는 매서드를 호출한다.")
    void callGetReservationById() {
        // given
        given(reservationRepository.findById(anyLong())).willReturn(Optional.of(mockReservation));

        // when
        reservationQueryService.getReservation(1L, Function.identity());

        // then
        verify(reservationRepository, times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("reservation Id가 없다면 예약 관련 에러를 발생시킨다.")
    void callGetReservationByIdThrowsExceptionWhenNotFound() {
        // given
        given(reservationRepository.findById(anyLong())).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationQueryService.getReservation(1L, Function.identity()))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", ReservationErrorCode.RESERVATION_NOT_FOUND);
    }


    @Test
    @DisplayName("member Id로 reservation 리스트들을 가저오는 메서드를 호출한다.")
    void callGetReservationsByMemberId() {
        // given
        given(reservationRepository.findByMemberId(anyLong())).willReturn(anyList());

        // when
        reservationQueryService.getReservation(1L);

        // then
        verify(reservationRepository, times(1)).findByMemberId(anyLong());
    }

}