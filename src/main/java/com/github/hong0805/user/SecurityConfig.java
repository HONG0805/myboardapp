package com.github.hong0805.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/Login", "/SignUp", "/IdSearch", "/PwSearch", "/static/**").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/Login").loginProcessingUrl("/api/users/login")
				.defaultSuccessUrl("/", true) // 로그인 후 홈 화면으로 리디렉션
				.failureUrl("/Login?error=true").and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

		return http.build();
	}

}
