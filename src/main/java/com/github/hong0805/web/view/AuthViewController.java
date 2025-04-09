package com.github.hong0805.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

	@GetMapping("/login")
	public String loginPage(@RequestParam(required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "로그인에 실패했습니다.");
		}
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