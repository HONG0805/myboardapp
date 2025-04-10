package com.github.hong0805.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.hong0805.service.AuthService;
import com.github.hong0805.web.dto.*;
import com.github.hong0805.web.dto.user.ApiResponse;
import com.github.hong0805.web.dto.user.CheckIdResponse;
import com.github.hong0805.web.dto.user.CodeVerificationRequest;
import com.github.hong0805.web.dto.user.FindIdRequest;
import com.github.hong0805.web.dto.user.FindIdResponse;
import com.github.hong0805.web.dto.user.JwtResponse;
import com.github.hong0805.web.dto.user.LoginRequest;
import com.github.hong0805.web.dto.user.RegisterRequest;
import com.github.hong0805.web.dto.user.ResetPasswordRequest;
import com.github.hong0805.web.dto.user.VerificationRequest;
import com.github.hong0805.web.dto.user.VerifyCodeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {
	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@GetMapping("/check-id")
	public ResponseEntity<CheckIdResponse> checkUserId(@RequestParam String userID) {
		boolean exists = authService.checkUserIdExists(userID);
		return ResponseEntity.ok(new CheckIdResponse(exists));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		String token = authService.login(request);
		if (token == null) {
			return ResponseEntity.status(401).body(new ApiResponse(false, "아이디 또는 비밀번호가 틀렸습니다."));
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/find-id")
	public ResponseEntity<FindIdResponse> findId(@RequestBody FindIdRequest request) {
		return ResponseEntity.ok(authService.findUserId(request));
	}

	@PostMapping("/send-verification-code")
	public ResponseEntity<ApiResponse> sendVerificationCode(@RequestBody VerificationRequest request) {
		return ResponseEntity.ok(authService.sendVerificationCode(request));
	}

	@PostMapping("/verify-code")
	public ResponseEntity<VerifyCodeResponse> verifyCode(@RequestBody CodeVerificationRequest request) {
		boolean result = authService.verifyCode(request);
		return ResponseEntity.ok(new VerifyCodeResponse(result));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
		return ResponseEntity.ok(authService.resetPassword(request));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
	}
}