package com.jerry.ticketing.repository.reservation;

import com.jerry.ticketing.domain.reservation.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem,Long> {

}
