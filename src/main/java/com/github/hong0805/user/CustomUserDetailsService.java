package com.github.hong0805.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
		System.out.println("로그인 시도한 ID: " + userID);
		return userRepository.findByUserID(userID).map(user -> {
			System.out.println("DB에서 찾은 비밀번호: " + user.getUserPassword());
			return new CustomUserDetails(user);
		}).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userID));
	}

}
