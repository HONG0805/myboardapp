package com.github.hong0805.web.api;

import com.github.hong0805.security.JwtTokenProvider;
import com.github.hong0805.service.JjimService;
import com.github.hong0805.web.dto.jjim.JjimRequest;
import com.github.hong0805.web.dto.jjim.JjimResponse;
import com.github.hong0805.web.dto.user.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/jjim")
public class JjimApiController {

	@Autowired
	private JjimService jjimService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addJjim(@RequestBody JjimRequest request, HttpServletRequest httpRequest) {

		String token = jwtTokenProvider.resolveToken(httpRequest);
		String userId = jwtTokenProvider.getUserIdFromToken(token);

		try {
			jjimService.addJjim(request.getBbsID(), userId);
			return ResponseEntity.ok(new ApiResponse(true, "찜 추가되었습니다"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	@PostMapping("/remove")
	public ResponseEntity<ApiResponse> removeJjim(@RequestBody JjimRequest request, HttpServletRequest httpRequest) {

		String token = jwtTokenProvider.resolveToken(httpRequest);
		String userId = jwtTokenProvider.getUserIdFromToken(token);

		try {
			jjimService.removeJjim(request.getBbsID(), userId);
			return ResponseEntity.ok(new ApiResponse(true, "찜 취소되었습니다"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
		}
	}

	// 찜 목록 조회
	@GetMapping("/list")
	public List<JjimResponse> getUserJjims(String userId) {
		return jjimService.getUserJjims(userId);
	}

	// 찜 상태 확인
	@GetMapping("/check")
	public boolean checkIfAlreadyJjimmed(Long bbsId, String userId) {
		return jjimService.checkIfAlreadyJjimmed(bbsId, userId);
	}
}
