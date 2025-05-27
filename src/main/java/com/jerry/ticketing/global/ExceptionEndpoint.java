package com.jerry.ticketing.global;


import com.jerry.ticketing.domain.concert.Concert;
import com.jerry.ticketing.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionEndpoint {


    @ExceptionHandler(BusinessException.class)
    public void errorHandler(BusinessException e) {

        e.printStackTrace();


        Concert concert = new Concert();
        save(concert);

    }
}
