package com.jerry.ticketing.member.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfile {

    private String name;
    private String phoneNumber;

    public static UpdateProfile of(String name, String phoneNumber) {
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.name = name;
        updateProfile.phoneNumber = phoneNumber;
        return updateProfile;
    }

}
