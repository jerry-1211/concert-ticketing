package com.jerry.ticketing.member.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;


public class UpdateProfile {

    @Getter
    @NoArgsConstructor
    public static class Request {
        private String name;
        private String phoneNumber;

        public static Request of(String name, String phoneNumber) {
            Request request = new Request();
            request.name = name;
            request.phoneNumber = phoneNumber;
            return request;
        }
    }
}
