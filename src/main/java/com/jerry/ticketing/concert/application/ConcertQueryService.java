package com.jerry.ticketing.concert.application;

import com.jerry.ticketing.concert.domain.Concert;
import com.jerry.ticketing.concert.domain.port.ConcertRepository;
import com.jerry.ticketing.global.exception.common.BusinessException;
import com.jerry.ticketing.global.exception.ConcertErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ConcertQueryService {

    private final ConcertRepository concertRepository;

    @Transactional(readOnly = true)
    public <T> List<T> getAllConcerts(Function<Concert, T> mapper) {
        return concertRepository.findAll().stream().map(mapper).toList();
    }


    @Transactional(readOnly = true)
    public <T> T getConcertById(Long concertId, Function<Concert, T> mapper) {
        return mapper.apply(concertRepository.findById(concertId)
                .orElseThrow(() -> new BusinessException(ConcertErrorCode.CONCERT_NOT_FOUND)));
    }

}
