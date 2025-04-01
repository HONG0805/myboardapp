package com.github.hong0805.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {
	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public static String hashPassword(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	public static boolean checkPassword(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}
}