package com.jerry.ticketing.seat.application;


import com.jerry.ticketing.seat.domain.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SectionCommandService {


    @Transactional
    public void decrease(Section section) {
        section.decreaseRemainingSeats();
    }


}
