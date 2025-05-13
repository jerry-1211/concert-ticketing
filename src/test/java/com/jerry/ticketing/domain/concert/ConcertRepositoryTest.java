package com.jerry.ticketing.domain.concert;

import com.jerry.ticketing.Repository.concert.ConcertRepository;
import com.jerry.ticketing.domain.TestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConcertRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ConcertRepository concertRepository;

    @BeforeEach
    void setUp() {
        Concert concert = TestFixture.createConcert();

        entityManager.persist(concert);
        entityManager.flush();
    }

    @Test
    @DisplayName("제목으로 콘서트 조회")
    void findByTitle(){
        // When
        List<Concert> concerts = concertRepository.findByTitle("Cold Play");

        // Then
        assertThat(concerts).hasSize(1);
        assertThat(concerts.get(0).getTitle()).isEqualTo("Cold Play");

    }
}
