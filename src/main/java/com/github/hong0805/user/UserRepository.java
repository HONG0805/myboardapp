package com.github.hong0805.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	// 아이디로 사용자 조회
	Optional<User> findByUserID(String userID);

	// 이메일과 이름으로 사용자 조회 (아이디 찾기용)
	Optional<User> findByUserEmailAndUserName(String userEmail, String userName);

	// 이메일로 사용자 조회 (비밀번호 재설정용)
	Optional<User> findByUserEmail(String userEmail);

	// 아이디와 이메일로 사용자 존재 여부 확인 (비밀번호 찾기 인증용)
	Optional<User> findByUserIDAndUserEmail(String userID, String userEmail);

	// 이메일 중복 확인 (회원가입용)
	boolean existsByUserEmail(String userEmail);

	// 아이디 중복 확인 (회원가입용)
	boolean existsByUserID(String userID);

	Optional<User> findByUserIDAndUserNameAndUserEmail(String userID, String userName, String userEmail);
}
