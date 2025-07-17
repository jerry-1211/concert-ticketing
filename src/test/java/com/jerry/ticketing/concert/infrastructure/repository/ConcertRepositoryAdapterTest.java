package com.jerry.ticketing.concert.infrastructure.repository;

import com.jerry.ticketing.concert.domain.Concert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
class ConcertRepositoryAdapterTest {


    @Autowired
    private ConcertJpaRepository jpaRepository;

    private ConcertRepositoryAdapter concertRepositoryAdapter;

    @BeforeEach
    void setUp() {
        concertRepositoryAdapter = new ConcertRepositoryAdapter(jpaRepository);
    }


    @Test
    @DisplayName("콘서트를 저장 할 수 있다.")
    void saveConcert() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Concert concert = Concert.of("ColdPlay", dateTime, "일산 대화동", 100_000, "나의 최고의 POP 밴드", 10);


        // when
        Concert savedConcert = concertRepositoryAdapter.save(concert);

        // then
        assertThat(savedConcert)
                .extracting("title", "dateTime", "venue", "price", "description", "maxTicketsPerUser")
                .containsExactlyInAnyOrder("ColdPlay", dateTime, "일산 대화동", 100_000, "나의 최고의 POP 밴드", 10);
    }

    @Test
    @DisplayName("콘서트를 아이디로 찾을 수 있다.")
    void findConcertById() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Concert concert = Concert.of("ColdPlay", dateTime, "일산 대화동", 100_000, "나의 최고의 POP 밴드", 10);
        Concert savedConcert = concertRepositoryAdapter.save(concert);

        // when // then
        assertThat(concertRepositoryAdapter.findById(savedConcert.getId())).isNotEmpty();
    }


    @Test
    @DisplayName("콘서트를 모두 찾을 수 있다.")
    void findAllConcert() {
        // given
        OffsetDateTime dateTime = OffsetDateTime.now();
        Concert concert1 = Concert.of("ColdPlay", dateTime, "일산 대화동", 100_000, "나의 최고의 POP 밴드", 10);
        Concert concert2 = Concert.of("아이유", dateTime, "강남 터미널", 40_000, "아이유의 밤 콘서트", 5);
        concertRepositoryAdapter.save(concert1);
        concertRepositoryAdapter.save(concert2);

        // when
        List<Concert> allConcert = concertRepositoryAdapter.findAll();

        // then
        assertThat(allConcert).hasSize(2)
                .extracting("title", "dateTime", "venue", "price", "description", "maxTicketsPerUser")
                .containsExactlyInAnyOrder(
                        tuple("ColdPlay", dateTime, "일산 대화동", 100_000, "나의 최고의 POP 밴드", 10),
                        tuple("아이유", dateTime, "강남 터미널", 40_000, "아이유의 밤 콘서트", 5)
                );
    }


}