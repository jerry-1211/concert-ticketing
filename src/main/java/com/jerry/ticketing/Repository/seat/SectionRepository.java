package com.jerry.ticketing.Repository.seat;

import com.jerry.ticketing.domain.seat.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Long> {

}
