package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class JoinResponse {
	private final String userId;
	private final String userName;
	private final String userEmail;
	private final String message;

	public JoinResponse(String userId, String userName, String userEmail) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.message = "회원가입이 성공적으로 완료되었습니다";
	}

	public static JoinResponse from(User user) {
		return new JoinResponse(user.getId(), user.getName(), user.getEmail());
	}
}