package com.jerry.ticketing.reservation.infrastructure.repository;

import com.jerry.ticketing.reservation.domain.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem,Long> {

}
