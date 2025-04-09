package com.github.hong0805.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.hong0805.service.AuthService;
import com.github.hong0805.web.dto.*;

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
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
		String token = authService.login(request);
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