package com.jerry.ticketing.seat.domain;

import com.jerry.ticketing.global.exception.common.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SectionTest {

    @Test
    @DisplayName("구역에 좌석이 남아있지 않는 경우, 좌석 에러를 발생한다.")
    void checkConcertSeatAvailabilityAndThrow() {
        // given
        Section section = Section.of(1L, "A", 0);

        // when // then
        assertThatThrownBy(section::decreaseRemainingSeats)
                .isInstanceOf(BusinessException.class)
                .hasMessage("해당 섹션의 남은 좌석이 없습니다.");
    }


    @Test
    @DisplayName("구역에 좌석이 남아있는 경우, 좌석의 수를 감소시킨다.")
    void decreaseAvailableConcertSeatCount() {
        // given
        Section section = Section.of(1L, "A", 10);

        // when
        section.decreaseRemainingSeats();

        // then
        assertThat(section.getRemainingConcertSeats()).isEqualTo(9);
    }


}