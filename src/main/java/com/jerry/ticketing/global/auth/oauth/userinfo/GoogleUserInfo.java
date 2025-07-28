package com.jerry.ticketing.global.auth.oauth.userinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@Getter
@RequiredArgsConstructor
public class GoogleUserInfo {
    private final Map<String, Object> attribute;

    public String getId() {
        return (String) attribute.get("sub");
    }

    public String getName() {
        return (String) attribute.get("name");
    }

    public String getEmail() {
        return (String) attribute.get("email");
    }

    public String getPicture() {
        return (String) attribute.get("picture");
    }


    public static GoogleUserInfo of(String id, String name, String email, String picture) {
        Map<String, Object> attributes = Map.of(
                "sub", id,
                "name", name,
                "email", email,
                "picture", picture
        );

        return new GoogleUserInfo(attributes);
    }

}
