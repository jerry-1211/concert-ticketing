package com.jerry.ticketing.seat.infrastructure.repository.concertseat;

import com.jerry.ticketing.seat.domain.ConcertSeat;
import com.jerry.ticketing.seat.domain.enums.ConcertSeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

    List<ConcertSeat> findByConcertId(Long id);

    List<ConcertSeat> findByIdIn(List<Long> ids);

    List<ConcertSeat> findByExpireAtBeforeAndStatus(OffsetDateTime expireTime, ConcertSeatStatus status);

    @Query("SELECT cs FROM ConcertSeat cs, Concert c, Section st, Seat s " +
            "WHERE cs.concertId = c.id AND cs.sectionId = st.id  AND cs.seatId = s.id " +
            "AND c.id = :concertId AND st.zone = :zone AND s.seatRow = :row")
    List<ConcertSeat> findByJoinConditions(@Param("concertId") Long concertId,
                                           @Param("zone") String zone,
                                           @Param("row") String row);

}
