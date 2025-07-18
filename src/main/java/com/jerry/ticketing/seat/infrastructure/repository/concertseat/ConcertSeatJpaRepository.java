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


    @Query("SELECT cs FROM ConcertSeat cs " +
            "WHERE cs.concertId = :concertId " +
            "  AND cs.seatId IN ( " +
            "    SELECT s.id FROM Seat s " +
            "    WHERE s.seatRow = :row " +
            "  ) " +
            "  AND cs.sectionId IN ( " +
            "    SELECT st.id FROM Section st " +
            "    WHERE st.zone = :zone " +
            "  )")
    List<ConcertSeat> findByJoinConditions(@Param("concertId") Long concertId,
                                           @Param("zone") String zone,
                                           @Param("row") String row);

}
