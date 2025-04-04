package com.github.hong0805.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// 로그인 (비밀번호 해시 비교)
	public boolean login(String userID, String userPassword) {
		Optional<User> optionalUser = userRepository.findByUserID(userID);
		if (optionalUser.isPresent()
				&& SecurityUtil.checkPassword(userPassword, optionalUser.get().getUserPassword())) {
			return true; // 로그인 성공
		}
		return false; // 로그인 실패
	}

	// 아이디 중복 확인
	public boolean existsByUserID(String userID) {
		return userRepository.existsById(userID);
	}

	// 회원가입 (비밀번호 유효성 검사 및 해시화 후 저장)
	@Transactional
	public boolean register(User user) {
	    // 1. 아이디 중복 검사
	    if (userRepository.existsById(user.getUserID())) {
	        return false;
	    }
	    
	    // 2. 이메일 중복 검사
	    if (userRepository.existsByUserEmail(user.getUserEmail())) {
	        return false;
	    }
	    
	    // 3. 비밀번호 암호화 및 저장
	    user.setUserPassword(SecurityUtil.hashPassword(user.getUserPassword()));
	    userRepository.save(user);
	    return true;
	}

	// 비밀번호 변경 (아이디 기반)
	public boolean changePassword(String userID, String newPassword) {
		Optional<User> optionalUser = userRepository.findByUserID(userID);
		if (optionalUser.isPresent() && isValidPassword(newPassword)) {
			User user = optionalUser.get();
			user.setUserPassword(SecurityUtil.hashPassword(newPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	// 비밀번호 변경 (이메일 기반)
	public boolean changePasswordByEmail(String userEmail, String newPassword) {
		Optional<User> optionalUser = userRepository.findByUserEmail(userEmail);
		if (optionalUser.isPresent() && isValidPassword(newPassword)) {
			User user = optionalUser.get();
			user.setUserPassword(SecurityUtil.hashPassword(newPassword));
			userRepository.save(user);
			return true;
		}
		return false;
	}

	// 아이디 찾기 (이름과 이메일로 아이디 찾기)
	public String findId(String userName, String userEmail) {
		Optional<User> optionalUser = userRepository.findByUserEmailAndUserName(userEmail, userName);
		return optionalUser.map(User::getUserID).orElse(null); // 아이디를 반환하거나, 없으면 null
	}

	// 비밀번호 유효성 검사
	public boolean isValidPassword(String password) {
		// 최소 8자, 영문+숫자+특수문자 포함
		String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$";
		return password.matches(pattern);
	}

	// 사용자 ID와 이름, 이메일로 조회 (인증 코드 전송 시 사용)
	public Optional<User> findByUserIDAndUserNameAndUserEmail(String userID, String userName, String userEmail) {
		return userRepository.findByUserIDAndUserNameAndUserEmail(userID, userName, userEmail);
	}

	// 이메일로 사용자 조회
	public Optional<User> findByUserEmail(String userEmail) {
		return userRepository.findByUserEmail(userEmail);
	}
}