package com.jerry.ticketing.member.application.dto;


import com.jerry.ticketing.member.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UpdateProfile {

    @Getter
    @NoArgsConstructor
    public static class Request {

        private String name;
        private Address address;
        private String phoneNumber;

    }

}
