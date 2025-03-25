package com.github.hong0805.user.dto.response;

import com.github.hong0805.user.User;
import lombok.Getter;

@Getter
public class UserResponse {
	private final String userId;
	private final String userName;
	private final String userEmail;
	private final String joinDate;

	public UserResponse(String userId, String userName, String userEmail, String joinDate) {
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.joinDate = joinDate;
	}

	public static UserResponse from(User user) {
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt().toString());
	}
}