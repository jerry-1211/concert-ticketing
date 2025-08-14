package com.jerry.ticketing.reservation.application;

import com.jerry.ticketing.global.exception.ReservationErrorCode;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.reservation.application.dto.domain.ReservationDto;
import com.jerry.ticketing.reservation.application.dto.web.CreateReservationDto;
import com.jerry.ticketing.reservation.domain.Reservation;
import com.jerry.ticketing.reservation.domain.enums.ReservationStatus;
import com.jerry.ticketing.reservation.domain.port.ReservationRepository;
import com.jerry.ticketing.reservation.util.OrderIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationCommandServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationQueryService reservationQueryService;

    @Mock
    private ReservationFacade reservationFacade;

    @Mock
    private OrderIdGenerator orderIdGenerator;

    @Mock
    private Reservation mockReservation;


    @InjectMocks
    private ReservationCommandService reservationCommandService;


    @Test
    @DisplayName("request 매개변수(예약 생성 요청)로 예약을 만들고 저장한 후 response를 반환한다.")
    void createReservationByRequestAndReturnResponse() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);

        CreateReservationDto.Request request = CreateReservationDto.Request.of(
                "token-123", 1L, "orderName-123", dateTime, 10_000, 3
        );

        given(reservationFacade.create(request)).willReturn(reservation);
        given(reservationRepository.save(reservation)).willReturn(reservation);

        CreateReservationDto.Response from = CreateReservationDto.Response.from(reservation);


        // when
        CreateReservationDto.Response response = reservationCommandService.create(request);

        // then
        verify(reservationFacade, times(1)).create(request);
        verify(reservationRepository, times(1)).save(reservation);

        assertThat(response)
                .extracting("orderName", "totalAmount", "reservationId")
                .containsExactlyInAnyOrder(from.getOrderName(), from.getTotalAmount(), from.getReservationId());
    }

    @Test
    @DisplayName("orderId로 예약을 찾은 후 확정하는 메서드를 호출한다. ")
    void confirmReservationByOrderId() {
        // given
        String orderId = "orderId-123";
        given(reservationRepository.findByOrderId(orderId)).willReturn(Optional.of(mockReservation));

        // when
        reservationCommandService.confirm(orderId);

        // then
        verify(reservationRepository, times(1)).findByOrderId(orderId);
        verify(mockReservation, times(1)).confirmReservation();

    }

    @Test
    @DisplayName("orderId으로 된 예약이 존재하지 않은 경우  reservation 에러코드를 발생시킨다.")
    void throwReservationErrorIfNotFoundByOrderId() {
        // given
        given(reservationRepository.findByOrderId(anyString())).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationCommandService.confirm("orderId-123"))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("ErrorCode", ReservationErrorCode.RESERVATION_NOT_FOUND);
    }

    @Test
    @DisplayName("만료 기한이 지난 예약들을 찾은 후, cancel 한다.")
    void cancelExpiredReservations() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Reservation reservation1 = Reservation.of(1L, 1L,
                "ORDER-123", dateTime, dateTime, 10_000, 3);
        Reservation reservation2 = Reservation.of(2L, 1L,
                "ORDER-456", dateTime, dateTime, 10_000, 3);

        List<Reservation> reservations = List.of(reservation1, reservation2);

        given(reservationRepository.findByExpiresAtBeforeAndStatus(any(OffsetDateTime.class), eq(ReservationStatus.PENDING)))
                .willReturn(reservations);

        // when
        reservationCommandService.releaseExpiredReservations();

        // then
        verify(reservationRepository, times(1))
                .findByExpiresAtBeforeAndStatus(any(OffsetDateTime.class), eq(ReservationStatus.PENDING));

        reservations.forEach(reservation -> {
            assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
        });
    }

    @Test
    @DisplayName("파라미터(reservation ID)로 예약을 찾은 후, order ID를 업데이트하고 DTO로 반환한다.")
    void updateOrderIdAndReturnDtoByReservationId() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();

        Reservation reservation = Reservation.of(1L, 1L, "ORDER-123", dateTime, dateTime, 10_000, 3);
        Long reservationId = 1L;
        String orderId = "orderId-123";

        given(reservationQueryService.getReservation(reservationId, Function.identity()))
                .willReturn(reservation);
        given(orderIdGenerator.generate(reservationId)).willReturn(orderId);

        ReservationDto from = ReservationDto.from(reservation);

        // when
        ReservationDto reservationDto = reservationCommandService.updateOrderId(reservationId);

        // then
        assertThat(reservation.getOrderId()).isEqualTo(orderId);

        assertThat(reservationDto)
                .extracting("totalAmount", "createdAt", "expiresAt", "quantity", "orderName")
                .containsExactlyInAnyOrder(from.getTotalAmount(), from.getCreatedAt(), from.getExpiresAt(), from.getQuantity(), from.getOrderName());
    }


}