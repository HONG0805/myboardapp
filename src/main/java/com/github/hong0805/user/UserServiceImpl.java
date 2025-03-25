package com.github.hong0805.user;

import com.github.hong0805.user.User;
import com.github.hong0805.user.UserRepository;
import com.github.hong0805.user.dto.request.*;
import com.github.hong0805.user.dto.response.*;
import com.github.hong0805.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public LoginResponse login(LoginRequest request) {
		User user = userRepository.findById(request.getId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Invalid password");
		}

		return LoginResponse.from(user);
	}

	@Override
	@Transactional
	public JoinResponse join(JoinRequest request) {
		if (userRepository.existsById(request.getId())) {
			throw new IllegalArgumentException("User ID already exists");
		}
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email already exists");
		}

		User user = User.builder().id(request.getId()).password(passwordEncoder.encode(request.getPassword()))
				.email(request.getEmail()).name(request.getName()).build();

		User savedUser = userRepository.save(user);
		return JoinResponse.from(savedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public FindIdResponse findId(FindIdRequest request) {
		User user = userRepository.findByEmailAndName(request.getEmail(), request.getName())
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		return FindIdResponse.from(user);
	}

	@Override
	@Transactional
	public void resetPassword(ResetPasswordRequest request) {
		User user = userRepository.findByIdAndEmail(request.getId(), request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void changePassword(ChangePasswordRequest request) {
		User user = userRepository.findById(request.getId())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Current password is incorrect");
		}

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
		return UserResponse.from(user);
	}

	@Override
	@Transactional(readOnly = true)
	public String findPassword(FindPasswordRequest request) {
		User user = userRepository.findByIdAndNameAndEmail(request.getId(), request.getName(), request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		return user.getPassword(); // 실제 운영시에는 임시 비밀번호 발급 로직 필요
	}
}