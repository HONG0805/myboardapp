package com.github.hong0805.user;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	// 인증 코드 저장을 위한 임시 저장소
	private Map<String, String> verificationCodes = new ConcurrentHashMap<>();

	// 로그인 페이지 요청
	@GetMapping("/login")
	public String loginPage() {
		return "Login";
	}

	// 로그인 처리
	@PostMapping("/login")
	public String login(@RequestParam String userID, @RequestParam String userPassword,
			RedirectAttributes redirectAttributes) {
		System.out.println("Login attempt - ID: " + userID);
		boolean success = userService.login(userID, userPassword);
		if (success) {
			return "redirect:/bbs";
		} else {
			redirectAttributes.addFlashAttribute("message", "아이디 또는 비밀번호가 잘못되었습니다");
			return "redirect:/user/login";
		}
	}

	// 회원가입 페이지 요청
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new User());
		return "SignUp";
	}

	// 아이디 중복 확인
	@GetMapping("/checkID")
	@ResponseBody
	public String checkID(@RequestParam String userID) {
		boolean exists = userService.existsByUserID(userID);
		if (exists) {
			return "이미 사용 중인 아이디입니다.";
		}
		return "사용 가능한 아이디입니다.";
	}

	// 회원가입 처리
	@PostMapping("/register")
	public String register(@ModelAttribute User user, @RequestParam String userPasswordCheck,
			RedirectAttributes redirectAttributes) {

		System.out.println("전달받은 사용자 정보: " + user.getUserID() + ", " + user.getUserEmail());

		try {
			// 1. 비밀번호 일치 확인
			if (!user.getUserPassword().equals(userPasswordCheck)) {
				redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다");
				return "redirect:/user/register";
			}

			// 2. 비밀번호 유효성 검사
			String rawPassword = user.getUserPassword();
			if (!isValidPassword(rawPassword)) {
				System.out.println("비밀번호 검증 실패 (원본): " + rawPassword);
				redirectAttributes.addFlashAttribute("error", "비밀번호는 8~20자의 영문, 숫자, 특수문자를 포함해야 합니다.");
				return "redirect:/user/register";
			}

			// 3. 비밀번호 암호화
			String encodedPassword = SecurityUtil.hashPassword(rawPassword);
			user.setUserPassword(encodedPassword);
			System.out.println("해시된 비밀번호: " + encodedPassword);

			// 4. 회원가입 처리
			boolean success = userService.register(user);
			System.out.println("회원가입 결과: " + success);

			if (success) {
				// 5. 성공 시 처리
				redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");
				return "redirect:/user/login";
			} else {
				redirectAttributes.addFlashAttribute("error", "회원가입 실패: 아이디가 이미 사용 중입니다.");
				return "redirect:/user/register";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("회원가입 오류: " + e.getMessage());
			redirectAttributes.addFlashAttribute("error", "회원가입 처리 중 오류가 발생했습니다: " + e.getMessage());
			return "redirect:/user/register";
		}
	}

	// 비밀번호 찾기 페이지 요청
	@GetMapping("/findPassword")
	public String findPasswordPage(Model model) {
		model.addAttribute("codeSent", false);
		return "PwSearch";
	}

	// 인증 코드 전송
	@PostMapping("/sendVerificationCode")
	@ResponseBody
	public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> requestData) {
		try {
			String userName = requestData.get("userName");
			String userID = requestData.get("userID");
			String userEmail = requestData.get("userEmail");

			// 사용자 정보 확인
			Optional<User> userOpt = userRepository.findByUserIDAndUserNameAndUserEmail(userID, userName, userEmail);
			if (!userOpt.isPresent()) {
				return ResponseEntity.badRequest().body("일치하는 회원 정보가 없습니다.");
			}

			// 인증 코드 생성 (6자리 난수)
			String code = String.format("%06d", new Random().nextInt(999999));
			verificationCodes.put(userEmail, code);

			// 이메일 전송
			try {
				SimpleMailMessage message = new SimpleMailMessage();
				message.setTo(userEmail);
				message.setSubject("[강남대학교 중고장터] 비밀번호 찾기 인증 코드");
				message.setText("인증 코드: " + code);
				mailSender.send(message);

				return ResponseEntity.ok("인증 코드가 발송되었습니다.");
			} catch (Exception e) {
				// 이메일 전송 오류 처리
				e.printStackTrace(); // 오류 로그 출력
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 발송 중 오류가 발생했습니다.");
			}
		} catch (Exception e) {
			// 전체 메소드 예외 처리
			e.printStackTrace(); // 오류 로그 출력
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
		}
	}

	// 인증 코드 확인
	@PostMapping("/verifyCode")
	@ResponseBody
	public ResponseEntity<String> verifyCode(@RequestParam String userEmail, @RequestParam String code,
			HttpSession session) {

		String savedCode = verificationCodes.get(userEmail);
		if (savedCode == null || !savedCode.equals(code)) {
			return ResponseEntity.badRequest().body("인증 코드가 일치하지 않습니다.");
		}

		// 인증 성공 - 세션에 이메일 저장
		session.setAttribute("verifiedEmail", userEmail);
		verificationCodes.remove(userEmail); // 사용한 코드 제거

		return ResponseEntity.ok("인증 성공");
	}

	@PostMapping("/changePassword")
	public String processPasswordChange(@RequestParam String newPassword, @RequestParam String confirmPassword,
			HttpSession session, RedirectAttributes redirectAttributes) {

		// 세션에서 이메일 가져오기
		String userEmail = (String) session.getAttribute("verifiedEmail");
		if (userEmail == null) {
			return "redirect:/user/findPassword";
		}

		// 비밀번호 확인
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "redirect:/user/changePassword";
		}

		// 비밀번호 변경 처리
		boolean success = userService.changePasswordByEmail(userEmail, newPassword);
		if (success) {
			session.removeAttribute("verifiedEmail"); // 세션 정리
			redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
			return "redirect:/user/login";
		} else {
			redirectAttributes.addFlashAttribute("error", "비밀번호 변경에 실패했습니다.");
			return "redirect:/user/changePassword";
		}
	}

	// 비밀번호 변경 페이지
	@GetMapping("/changePassword")
	public String changePasswordPage(HttpSession session, Model model) {
		if (session.getAttribute("verifiedEmail") == null) {
			return "redirect:/user/findPassword";
		}
		return "ChangePW_1";
	}

	private boolean isValidPassword(String password) {
		// 비밀번호 정규식: 영문, 숫자, 특수문자 포함 8~20자리
		String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$";
		return password.matches(passwordPattern);
	}

	// 아이디 찾기 페이지 요청
	@GetMapping("/findId")
	public String findIdPage() {
		return "IdSearch";
	}

	// 아이디 찾기 요청 (이름과 이메일로 조회 후 결과 페이지 이동)
	@PostMapping("/findId")
	public String findId(@RequestParam String userName, @RequestParam String userEmail,
			RedirectAttributes redirectAttributes) {

		System.out.println("아이디 찾기 요청 - 이름: " + userName + ", 이메일: " + userEmail);

		// 사용자 조회
		return userRepository.findByUserEmailAndUserName(userEmail, userName).map(user -> {
			String maskedId = maskUserId(user.getUserID());
			redirectAttributes.addFlashAttribute("userID", maskedId);
			return "redirect:/user/idSearchSuccess";
		}).orElseGet(() -> {
			redirectAttributes.addFlashAttribute("error", "일치하는 회원 정보가 없습니다.");
			return "redirect:/user/findId";
		});
	}

	// 아이디 마스킹 처리 (예: "te***t")
	private String maskUserId(String userId) {
		if (userId == null || userId.length() <= 3) {
			return userId;
		}
		return userId.substring(0, 2) + "***" + userId.substring(userId.length() - 1);
	}

	// 아이디 찾기 성공 페이지 요청
	@GetMapping("/idSearchSuccess")
	public String idSearchSuccessPage() {
		return "IdSearch_success";
	}
}
