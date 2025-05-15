package com.jerry.ticketing.repository.concert;

import com.jerry.ticketing.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByTitle(String title);
}
