package com.jerry.ticketing.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.OffsetDateTime;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ConcertCreateRequest {


    @NotBlank(message = "콘서트 제목은 필수입니다.")
    @Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
    private String title;

    @NotNull(message = "공연 날짜는 필수입니다.")
    @FutureOrPresent(message = "공연 날짜가 현재 또는 미래여야 합니다.")
    private OffsetDateTime dateTime;

    @NotBlank(message = "공연 장소는 필수입니다.")
    @Size(max = 100, message = "공연 장소는 100자를 초과할 수 없습니다.")
    private String venue;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이여야 합니다.")
    @Max(value = 100_000_000, message = "가격은 1억원을 초과할 수 없습니다.")
    private int price;

    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    private String description;

    @NotNull(message = "사용자당 최대 티켓 수는 필수입니다.")
    @Min(value = 1, message = "사용자당 최대 티켓 수는 1개 이상이여야 합니다.")
    @Max(value = 5, message = "사용자당 최대 티켓 수는 5개 이하이여야 합니다.")
    private int maxTicketsPerUser;



}
