package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class FindPasswordResponse {
    private final String userId;
    private final String maskedPassword;
    private final String message;

    public FindPasswordResponse(String userId, String password) {
        this.userId = userId;
        this.maskedPassword = maskPassword(password);
        this.message = "비밀번호 찾기 결과입니다 (보안을 위해 일부만 표시)";
    }

    public static FindPasswordResponse from(User user) {
        return new FindPasswordResponse(
            user.getId(),
            user.getPassword()
        );
    }

    private String maskPassword(String password) {
        if (password == null || password.length() < 3) {
            return "***";
        }
        return password.substring(0, 1) + "***" + password.substring(password.length() - 1);
    }
}