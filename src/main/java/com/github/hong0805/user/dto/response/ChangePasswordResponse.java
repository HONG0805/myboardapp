package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class ChangePasswordResponse {
	private final String userId;
	private final String message;

	public ChangePasswordResponse(String userId) {
		this.userId = userId;
		this.message = "비밀번호가 성공적으로 변경되었습니다";
	}

	public static ChangePasswordResponse from(User user) {
		return new ChangePasswordResponse(user.getId());
	}
}