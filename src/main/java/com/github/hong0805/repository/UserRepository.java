package com.github.hong0805.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.hong0805.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	// 아이디 중복 체크
	boolean existsByUserId(String userId);
	
	// 이메일 중복 체크
	boolean existsByUserEmail(String userEmail);
	
	// 아이디 찾기
	Optional<User> findByUserNameAndUserEmail(String userName, String userEmail);

	// 인증코드 전송 시 사용자 확인
	Optional<User> findByUserIdAndUserEmailAndUserName(String userId, String userEmail, String userName);
	
	// 비밀번호 변경 시 사용자 조회
	Optional<User> findByUserEmail(String userEmail);

	// 사용자 조회
	Optional<User> findByUserId(String userID);


}
