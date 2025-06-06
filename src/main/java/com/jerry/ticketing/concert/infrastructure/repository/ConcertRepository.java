package com.jerry.ticketing.concert.infrastructure.repository;

import com.jerry.ticketing.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByTitle(String title);
}
