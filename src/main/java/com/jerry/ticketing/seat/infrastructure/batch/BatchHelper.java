package com.jerry.ticketing.seat.infrastructure.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchHelper {

    private static final int BATCH_SIZE = 1000;

    public <T> boolean hasRemaining(List<T> batch) {
        return !batch.isEmpty();
    }

    public <T> boolean isFull(List<T> batch) {
        return batch.size() >= BATCH_SIZE;
    }

}
