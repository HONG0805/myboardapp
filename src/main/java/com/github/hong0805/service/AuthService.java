package com.github.hong0805.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.hong0805.domain.User;
import com.github.hong0805.repository.UserRepository;
import com.github.hong0805.security.JwtTokenProvider;
import com.github.hong0805.web.dto.user.*;
import com.github.hong0805.web.dto.user.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final EmailService emailService;

	// 회원가입
	public ApiResponse register(RegisterRequest request) {
		if (userRepository.existsByUserId(request.getUserID())) {
			return new ApiResponse(false, "이미 존재하는 아이디입니다.");
		}

		// 이메일 중복 체크 추가
		if (userRepository.existsByUserEmail(request.getUserEmail())) {
			return new ApiResponse(false, "이미 사용 중인 이메일입니다.");
		}

		User user = User.builder().userId(request.getUserID())
				.userPassword(passwordEncoder.encode(request.getUserPassword())).userEmail(request.getUserEmail())
				.userName(request.getUserName()).build();

		userRepository.save(user);
		return new ApiResponse(true, "회원가입이 완료되었습니다.");
	}

	// 아이디 중복 확인
	public boolean checkUserIdExists(String userId) {
		return userRepository.existsByUserId(userId);
	}

	// 로그인
	public String login(LoginRequest request) {
		User user = userRepository.findByUserId(request.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));
		log.info("로그인 시도: {}", request.getUserId());

		if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		return jwtTokenProvider.createToken(user.getUserId());
	}

	// 아이디 찾기
	public FindIdResponse findUserId(FindIdRequest request) {
		User user = userRepository.findByUserNameAndUserEmail(request.getUserName(), request.getUserEmail())
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원 정보가 없습니다."));
		return new FindIdResponse(user.getUserId());
	}

	// 이메일 인증코드 전송
	public ApiResponse sendVerificationCode(VerificationRequest request) {
		// 사용자 검증
		User user = userRepository
				.findByUserIdAndUserEmailAndUserName(request.getUserID(), request.getUserEmail(), request.getUserName())
				.orElseThrow(() -> new IllegalArgumentException("입력한 정보가 일치하지 않습니다."));

		String code = generate6DigitCode();
		emailService.saveVerificationCode(user.getUserEmail(), code);
		emailService.sendCodeToEmail(user.getUserEmail(), code);

		return new ApiResponse(true, "인증코드가 발송되었습니다.");
	}

	// 6자리 인증 코드 생성 (랜덤)
	private String generate6DigitCode() {
		return String.valueOf((int) ((Math.random() * 900000) + 100000));
	}

	// 인증 코드 확인
	public boolean verifyCode(CodeVerificationRequest request) {
		return emailService.verifyCode(request.getUserEmail(), request.getCode());
	}

	// 비밀번호 재설정
	public ApiResponse resetPassword(ResetPasswordRequest request) {
		User user = userRepository.findByUserEmail(request.getUserEmail())
				.orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

		user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
		return new ApiResponse(true, "비밀번호가 변경되었습니다.");
	}

	// 사용자 아이디 조회
	public UserResponse getUserById(String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다: " + userId));

		return UserResponse.builder().userId(user.getUserId()).userName(user.getUserName())
				.userEmail(user.getUserEmail()).build();
	}

	// 사용자 이름 조회
	public String getUserName(String userId) {
		User user = userRepository.findByUserId(userId)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
		return user.getUserName();
	}

}