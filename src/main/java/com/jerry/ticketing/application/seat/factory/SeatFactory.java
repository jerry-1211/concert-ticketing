package com.jerry.ticketing.application.seat.factory;


import com.jerry.ticketing.application.seat.enums.SectionType;
import com.jerry.ticketing.domain.seat.Seat;
import com.jerry.ticketing.repository.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SeatFactory {


    // 좌석 생성 배치 크기
    private static final int BATCH_SIZE = 1000;
    private final SeatRepository seatRepository;

    /**
     * 초기 좌석(VO객체)을 생성합니다.
     * */
    public void initializeSeats(){

        if (seatRepository.count()>0) {
            log.info("좌석 데이터가 이미 존재합니다. 초기화를 건너뜁니다.");
            return;
        }
        log.info("좌석 초기화를 시작합니다...");
        initializeAllSeats();
        log.info("좌석 데이터 초기화가 완료되었습니다.");
    }


    /**
     * 애플리케이션 시작 시 좌석 데이터 초기화 데이터 지정합니다.
     * */
    protected void initializeAllSeats(){
        for(char zone = 'A'; zone<= 'F'; zone ++){
            createSeats(SectionType.VIP);
        }

        for(char zone = 'G'; zone<= 'L'; zone ++){
            createSeats(SectionType.STANDARD);
        }

        for(char zone = 'M'; zone<= 'Z'; zone ++){
            createSeats(SectionType.ECONOMY);
        }

    }


    /**
     * Section Config를 기반으로 실제 Seat 객체를 생성합니다.
     * */
    private void createSeats(SectionType type) {
        List<Seat> seatBatch = new ArrayList<>(BATCH_SIZE);
        int totalCreated = 0;

        for (char rowChar = type.getStartRow(); rowChar <= type.getEndRow(); rowChar++) {
            char row = rowChar;
            for (int number = type.getStartNumber(); number <= type.getEndNumber(); number++) {
                Seat seat = Seat.createSeat(row, number, type.getSeatType());
                seatBatch.add(seat);

                if (seatBatch.size() >= BATCH_SIZE){
                    seatRepository.saveAll(seatBatch);
                    totalCreated += seatBatch.size();
                    seatBatch.clear();
                    log.debug("{}개 좌석 저장 완료", totalCreated);
                }
            }
        }

        if(!seatBatch.isEmpty()){
            seatRepository.saveAll(seatBatch);
            totalCreated += seatBatch.size();
        }

        log.debug("총 {}개 좌석 생성 완료", totalCreated);
    }

}

