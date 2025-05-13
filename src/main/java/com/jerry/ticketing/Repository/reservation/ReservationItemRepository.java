package com.jerry.ticketing.Repository.reservation;

import com.jerry.ticketing.domain.reservation.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItem,Long> {

}
