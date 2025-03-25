package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class ResetPasswordResponse {
	private final String userId;
	private final String message;

	public ResetPasswordResponse(String userId) {
		this.userId = userId;
		this.message = "비밀번호가 성공적으로 재설정되었습니다";
	}

	public static ResetPasswordResponse from(User user) {
		return new ResetPasswordResponse(user.getId());
	}
}