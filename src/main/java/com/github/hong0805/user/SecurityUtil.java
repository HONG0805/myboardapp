package com.github.hong0805.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {
	private static PasswordEncoder passwordEncoder;

	public static void setPasswordEncoder(PasswordEncoder encoder) {
		passwordEncoder = encoder;
	}

	public static String hashPassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public static boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}