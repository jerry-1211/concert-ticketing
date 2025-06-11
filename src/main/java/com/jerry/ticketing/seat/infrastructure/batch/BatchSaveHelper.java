package com.jerry.ticketing.seat.infrastructure.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchSaveHelper {
    private static final int BATCH_SIZE = 1000;

    public <T> int saveRemaining(List<T> batch, int totalCreated, JpaRepository<T, ?> repository) {
        if (!batch.isEmpty()) {
            repository.saveAll(batch);
            totalCreated += batch.size();
        }
        return totalCreated;
    }

    public <T> int saveIfFull(List<T> batch, int totalCreated, JpaRepository<T, ?> repository) {
        if (batch.size() >= BATCH_SIZE) {
            repository.saveAll(batch);
            totalCreated += batch.size();
            batch.clear();
        }
        return totalCreated;
    }

}
