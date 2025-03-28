package com.github.hong0805.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 로그인 페이지 요청
	@GetMapping("/login")
	public String loginPage() {
		return "Login"; // Login.html을 반환
	}

	// 로그인 처리
	@PostMapping("/login")
	public String login(@RequestParam String userID, @RequestParam String userPassword,
			RedirectAttributes redirectAttributes) {
		boolean success = userService.login(userID, userPassword);
		if (success) {
			return "redirect:/home"; // 로그인 성공 시 홈으로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "로그인 실패");
			return "redirect:/user/login"; // 로그인 실패 시 로그인 페이지로 리디렉션
		}
	}

	// 회원가입 페이지 요청
	@GetMapping("/register")
	public String registerPage() {
		return "SignUp"; // SignUp.html을 반환
	}

	// 회원가입 처리
	@PostMapping("/register")
	public String register(@RequestParam String userID, @RequestParam String userPassword,
			@RequestParam String userEmail, @RequestParam String userName, RedirectAttributes redirectAttributes) {
		User user = new User();
		user.setUserID(userID);
		user.setUserPassword(userPassword);
		user.setUserEmail(userEmail);
		user.setUserName(userName);

		boolean success = userService.register(user);
		if (success) {
			return "redirect:/user/login"; // 회원가입 성공 후 로그인 페이지로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "아이디 중복");
			return "redirect:/user/register"; // 회원가입 실패 시 회원가입 페이지로 리디렉션
		}
	}

	// 비밀번호 변경 페이지 요청
	@GetMapping("/changePassword")
	public String changePasswordPage() {
		return "ChangePW_1"; // ChangePW_1.html을 반환
	}

	// 비밀번호 변경 처리
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam String userID, @RequestParam String newPassword,
			RedirectAttributes redirectAttributes) {
		boolean success = userService.changePassword(userID, newPassword);
		if (success) {
			return "redirect:/user/login"; // 비밀번호 변경 성공 후 로그인 페이지로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "비밀번호 변경 실패");
			return "redirect:/user/changePassword"; // 비밀번호 변경 실패 시 변경 페이지로 리디렉션
		}
	}

	// 아이디 찾기 페이지 요청
	@GetMapping("/findId")
	public String findIdPage() {
		return "IdSearch"; // IdSearch.html을 반환
	}

	// 아이디 찾기 처리
	@PostMapping("/findId")
	public String findId(@RequestParam String userName, @RequestParam String userEmail,
			RedirectAttributes redirectAttributes) {
		String userID = userService.findId(userName, userEmail);
		if (userID != null) {
			redirectAttributes.addFlashAttribute("userID", userID);
			return "redirect:/user/idSearchSuccess"; // 아이디 찾기 성공 후 결과 페이지로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "아이디를 찾을 수 없습니다.");
			return "redirect:/user/findId"; // 아이디 찾기 실패 시 다시 아이디 찾기 페이지로 리디렉션
		}
	}

	// 아이디 찾기 성공 페이지 요청
	@GetMapping("/idSearchSuccess")
	public String idSearchSuccessPage() {
		return "IdSearch_sucess"; // IdSearch_sucess.html을 반환
	}
}
