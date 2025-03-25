package com.github.hong0805.user;

import com.github.hong0805.user.UserService;
import com.github.hong0805.user.dto.request.*;
import com.github.hong0805.user.dto.response.*;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
		return ResponseEntity.ok(userService.login(request));
	}

	@PostMapping("/join")
	public ResponseEntity<JoinResponse> join(@RequestBody @Valid JoinRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.join(request));
	}

	@PostMapping("/find-id")
	public ResponseEntity<FindIdResponse> findId(@RequestBody @Valid FindIdRequest request) {
		return ResponseEntity.ok(userService.findId(request));
	}

	@PutMapping("/reset-password")
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
		userService.resetPassword(request);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/change-password")
	public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
		userService.changePassword(request);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@PostMapping("/find-password")
	public ResponseEntity<String> findPassword(@RequestBody @Valid FindPasswordRequest request) {
		return ResponseEntity.ok(userService.findPassword(request));
	}
}