package com.github.hong0805.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@Column(name = "userID", nullable = false, unique = true)
	private String userId;

	@Column(name = "userPassword", nullable = false)
	private String userPassword;

	@Column(name = "userEmail", nullable = false, unique = true)
	private String userEmail;

	@Column(name = "userName", nullable = false)
	private String userName;

	@Builder
	public User(String userId, String userPassword, String userEmail, String userName) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userName = userName;
	}

	// 비밀번호 변경 메서드 추가
	public void updatePassword(String newPassword) {
		this.userPassword = newPassword;
	}
}
