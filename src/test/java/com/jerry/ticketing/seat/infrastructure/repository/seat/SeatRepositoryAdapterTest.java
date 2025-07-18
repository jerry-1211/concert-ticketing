package com.jerry.ticketing.seat.infrastructure.repository.seat;

import com.jerry.ticketing.seat.domain.Seat;
import com.jerry.ticketing.seat.domain.enums.SeatType;
import com.jerry.ticketing.seat.domain.port.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class SeatRepositoryAdapterTest {

    @Autowired
    private SeatJpaRepository repository;

    private SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        seatRepository = new SeatRepositoryAdapter(repository);
    }

    @Test
    @DisplayName("좌석들을 모두 저장이 가능한다.")
    void saveAllSeats() {
        // given
        Seat seat1 = Seat.of("A", 10, SeatType.ECONOMY);
        Seat seat2 = Seat.of("A", 11, SeatType.ECONOMY);
        Seat seat3 = Seat.of("A", 12, SeatType.ECONOMY);

        // when
        List<Seat> seats = seatRepository.saveAll(List.of(seat1, seat2, seat3));

        // then
        assertThat(seats).hasSize(3)
                .extracting("seatRow", "number", "seatType")
                .containsExactlyInAnyOrder(
                        tuple("A", 10, SeatType.ECONOMY),
                        tuple("A", 11, SeatType.ECONOMY),
                        tuple("A", 12, SeatType.ECONOMY)
                );
    }


    @Test
    @DisplayName("id들로 모든 좌석들을 찾을 수 있다.")
    void findAllSeatsById() {
        // given
        Seat seat1 = Seat.of("A", 10, SeatType.ECONOMY);
        Seat seat2 = Seat.of("A", 11, SeatType.ECONOMY);

        Seat savedSeat1 = seatRepository.save(seat1);
        Seat savedSeat2 = seatRepository.save(seat2);

        // when
        List<Seat> seats = seatRepository.findAllById(List.of(savedSeat1.getId(), savedSeat2.getId()));

        // then
        assertThat(seats).hasSize(2)
                .extracting("seatRow", "number", "seatType")
                .containsExactlyInAnyOrder(
                        tuple("A", 10, SeatType.ECONOMY),
                        tuple("A", 11, SeatType.ECONOMY)
                );
    }

    @Test
    @DisplayName("id로 좌석을 찾을 수 있다.")
    void findSeatById() {
        // given
        Seat seat1 = Seat.of("A", 10, SeatType.ECONOMY);
        Seat savedSeat1 = seatRepository.save(seat1);

        // when
        Optional<Seat> seat = seatRepository.findById(savedSeat1.getId());

        // then
        assertThat(seat).isPresent()
                .hasValueSatisfying(
                        s -> {
                            assertThat(s)
                                    .extracting("seatRow", "number", "seatType")
                                    .containsExactlyInAnyOrder("A", 10, SeatType.ECONOMY);
                        }
                );
    }

    @Test
    @DisplayName("모든 좌석을 찾을 수 있다.")
    void findAllSeats() {
        // given
        Seat seat1 = Seat.of("A", 10, SeatType.ECONOMY);
        Seat seat2 = Seat.of("A", 11, SeatType.ECONOMY);
        seatRepository.saveAll(List.of(seat1, seat2));

        // when
        List<Seat> seats = seatRepository.findAll();

        // then
        assertThat(seats).hasSize(2)
                .extracting("seatRow", "number", "seatType")
                .containsExactlyInAnyOrder(
                        tuple("A", 10, SeatType.ECONOMY),
                        tuple("A", 11, SeatType.ECONOMY)
                );
    }


}