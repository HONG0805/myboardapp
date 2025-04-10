package com.github.hong0805.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = resolveToken(request);

			if (token != null && jwtTokenProvider.validateToken(token)) {
				// 토큰에서 사용자 ID 추출 후 인증 객체 생성
				String userId = jwtTokenProvider.getUserIdFromToken(token);
				Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 유효하지 않은 토큰일 경우 401 응답
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
				return;
			}

			filterChain.doFilter(request, response);

		} catch (Exception e) {
			// 토큰 검증 중 예외 발생 시 401 응답
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + e.getMessage());
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();

		return path.startsWith("/auth") || path.startsWith("/api/auth") || path.startsWith("/css")
				|| path.startsWith("/js") || path.startsWith("/images") || path.equals("/");
	}

	private String resolveToken(HttpServletRequest request) {
		// 헤더에서 토큰 추출 (Bearer 방식)
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		
	    // 쿠키에서 토큰 추출
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("JWT".equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }

		return null;
	}
}