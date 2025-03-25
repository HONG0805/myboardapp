package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String userId;
    private final String userName;
    private final String userEmail;

    public LoginResponse(String userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public static LoginResponse from(User user) {
        return new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}