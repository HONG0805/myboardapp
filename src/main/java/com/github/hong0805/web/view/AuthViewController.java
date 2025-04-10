package com.github.hong0805.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

	@GetMapping("/login")
	public String loginPage() {
	    return "Login";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "SignUp";
	}

	@GetMapping("/find-id")
	public String findIdPage() {
		return "IdSearch";
	}

	@GetMapping("/reset-password")
	public String resetPasswordPage() {
		return "PwSearch";
	}

	@GetMapping("/change-password")
	public String changePasswordPage() {
		return "ChangePW_1";
	}
}