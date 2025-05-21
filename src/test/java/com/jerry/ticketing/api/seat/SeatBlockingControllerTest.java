package com.jerry.ticketing.api.seat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.ticketing.application.seat.SeatBlockingService;
import com.jerry.ticketing.domain.seat.ConcertSeat;
import com.jerry.ticketing.dto.request.SeatBlockingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest(SeatBlockingController.class)
class SeatBlockingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SeatBlockingService seatBlockingService;

    @Test
    @DisplayName("좌석 점유 성공 테스트")
    void blockSeats_Success() throws Exception {
        // Given
        SeatBlockingRequest request = new SeatBlockingRequest(1L, Arrays.asList(1L, 2L), 100L);
        ConcertSeat concertSeat1 = mock(ConcertSeat.class);
        ConcertSeat concertSeat2 = mock(ConcertSeat.class);

        when(concertSeat1.getId()).thenReturn(1L);
        when(concertSeat2.getId()).thenReturn(2L);
        when(concertSeat1.getBlockedExpireAt()).thenReturn(LocalDateTime.now().plusMinutes(10));
        when(seatBlockingService.blockSeats(anyLong(), anyList(), anyLong())).thenReturn(Arrays.asList(concertSeat1, concertSeat2));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/seats/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockedSeatIds.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockedSeatIds[0]").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockedSeatIds[1]").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockedSeatIds").exists());

    }
}