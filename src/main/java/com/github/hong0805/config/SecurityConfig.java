package com.github.hong0805.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.hong0805.security.JwtAuthFilter;
import com.github.hong0805.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				// 정적 리소스 및 공개 페이지 허용
				.antMatchers("/", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
				// 로그인, 회원가입 등 인증 불필요한 API
				.antMatchers("/api/auth/**", "/auth/**", "/login", "/bbs/main", "/bbs/view/**").permitAll()
				// 인증 필요한 페이지
				.antMatchers("/bbs/write", "/bbs/update/**", "/bbs/delete/**", "/bbs/jjim").authenticated()
				// 인증 필요한 API
				.antMatchers("/api/bbs/**", "/api/chat/**").authenticated()
				// 기타 모든 요청 허용
				.anyRequest().permitAll().and()
				.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
					if (isApiRequest(request)) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
					} else {
						response.sendRedirect("/auth/login");
					}
				});

		return http.build();
	}

	private boolean isApiRequest(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
