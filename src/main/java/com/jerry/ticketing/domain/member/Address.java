package com.jerry.ticketing.domain.member;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {

    // 도시
    private String city;

    // 구역
    private String street;

    public static Address of(String city,String street) {
        return new Address(city, street);
    }
}
