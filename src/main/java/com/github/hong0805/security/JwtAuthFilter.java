package com.github.hong0805.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	// 인증이 필요하지 않은 경로 목록
	private static final String[] WHITELIST = { "/auth/**", "/api/auth/**", "/css/**", "/js/**", "/images/**",
			"/favicon.ico", "/", "/bbs/main", "/bbs/view/**", "/error" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = resolveToken(request);
			log.debug(">>> 추출된 JWT 토큰: {}", token);

			if (token != null) {
				if (token.split("\\.").length == 3) {
					if (jwtTokenProvider.validateToken(token)) {
						String userId = jwtTokenProvider.getUserIdFromToken(token);
						log.debug("인증된 사용자: {}", userId);

						List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
						Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);
						SecurityContextHolder.getContext().setAuthentication(auth);
					} else {
						log.warn("유효하지 않은 JWT 토큰입니다.");
					}
				} else {
					log.warn("JWT 토큰 형식이 잘못되었습니다: {}", token);
				}
			} else {
				log.debug("요청에서 JWT 토큰을 찾을 수 없습니다.");
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			log.error("JWT 처리 중 오류 발생", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 실패");
		}
	}

	// 정적 리소스는 필터 건너뛰기
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")
				|| path.startsWith("/error");
	}

	String resolveToken(HttpServletRequest request) {
		// 1. 헤더에서 토큰 추출 (Bearer 방식)
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		// 2. 쿠키에서 토큰 추출
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("JWT".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}

		// 3. URL 파라미터에서 토큰 추출
		String tokenParam = request.getParameter("token");
		if (tokenParam != null && !tokenParam.isEmpty()) {
			return tokenParam;
		}

		return null;
	}

	private boolean requiresAuthentication(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/api/") || path.startsWith("/bbs/write") || path.startsWith("/bbs/update")
				|| path.startsWith("/bbs/delete") || path.startsWith("/bbs/jjim");
	}

	private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isApiRequest(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다.");
		} else {
			response.sendRedirect("/auth/login?error=invalid_token");
		}
	}

	private void handleMissingToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (isApiRequest(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 토큰이 필요합니다.");
		} else {
			response.sendRedirect("/auth/login?error=missing_token");
		}
	}

	private boolean isApiRequest(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
	}
}
