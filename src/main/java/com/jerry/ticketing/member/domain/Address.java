package com.jerry.ticketing.member.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    // 도시
    private String city;

    // 구역
    private String street;

    public Address(String city, String street) {
        this.city = city;
        this.street = street;
    }

    public static Address of(String city, String street) {
        return new Address(city, street);
    }
}
