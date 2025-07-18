package com.jerry.ticketing.concert.infrastructure.repository;

import com.jerry.ticketing.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

}
