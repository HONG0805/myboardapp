package com.github.hong0805.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// 비밀번호를 해시화하는 메서드
	public static String hashPassword(String password) {
		return passwordEncoder.encode(password);
	}

	// 비밀번호 비교하는 메서드
	public static boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
