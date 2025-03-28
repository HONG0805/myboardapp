package com.github.hong0805.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// 로그인 (비밀번호 해시 비교)
	public boolean login(String userID, String userPassword) {
		User user = userRepository.findByUserID(userID);
		if (user != null && SecurityUtil.checkPassword(userPassword, user.getUserPassword())) {
			return true; // 로그인 성공
		}
		return false; // 로그인 실패
	}

	// 회원가입 (비밀번호 해시화 후 저장)
	public boolean register(User user) {
		if (userRepository.existsById(user.getUserID())) {
			return false; // 중복된 아이디
		}
		user.setUserPassword(SecurityUtil.hashPassword(user.getUserPassword()));
		userRepository.save(user);
		return true; // 회원가입 성공
	}

	// 비밀번호 변경
	public boolean changePassword(String userID, String newPassword) {
		User user = userRepository.findByUserID(userID);
		if (user != null) {
			user.setUserPassword(SecurityUtil.hashPassword(newPassword));
			userRepository.save(user);
			return true;
		}
		return false; // 사용자 없음
	}

	// 아이디 찾기
	public String findId(String userName, String userEmail) {
		User user = userRepository.findByUserEmailAndUserName(userEmail, userName);
		return user != null ? user.getUserID() : null;
	}
}
