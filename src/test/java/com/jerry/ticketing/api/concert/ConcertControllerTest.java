package com.jerry.ticketing.api.concert;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.ticketing.application.concert.ConcertService;
import com.jerry.ticketing.dto.CreateConcert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConcertService concertService;

    @Test
    @DisplayName("콘서트 생성 성공 테스트")
    void createConcert_Success() throws Exception {
        // Given
        CreateConcert.Request request = CreateConcert.Request.builder()
                .title("Test-Title")
                .dateTime(OffsetDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES))
                .venue("Test-Venue")
                .price(100_000)
                .description("Test-Description")
                .maxTicketsPerUser(3)
                .build();

        CreateConcert.Response response = CreateConcert.Response.builder()
                .id(1L)
                .title("Test-Title")
                .dateTime(OffsetDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .venue("Test-Venue")
                .price(100_000)
                .description("Test-Description")
                .maxTicketsPerUser(3)
                .build();

        when(concertService.createConcert(any(CreateConcert.Request.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").exists());

    }

}