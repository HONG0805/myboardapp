package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class FindIdResponse {
	private final String userId;
	private final String message;

	public FindIdResponse(String userId) {
		this.userId = userId;
		this.message = "아이디 찾기 결과: " + userId;
	}

	public static FindIdResponse from(User user) {
		return new FindIdResponse(user.getId());
	}
}