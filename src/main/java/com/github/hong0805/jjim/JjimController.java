package com.github.hong0805.jjim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.hong0805.bbs.Bbs;

import java.util.List;

@RestController
@RequestMapping("/api/jjim")
public class JjimController {

	@Autowired
	private JjimService jjimService;

	// 찜 리스트 조회
	@GetMapping("/{userID}/list")
	public ResponseEntity<List<Bbs>> getJjimList(@PathVariable String userID, @RequestParam int pageNumber) {
		List<Bbs> jjimList = jjimService.getList(userID, pageNumber);
		return ResponseEntity.ok(jjimList);
	}

	// 찜 추가
	@PostMapping("/{userID}/add/{bbsID}")
	public ResponseEntity<String> addJjim(@PathVariable String userID, @PathVariable int bbsID) {
		String result = jjimService.addJjim(userID, bbsID);
		return ResponseEntity.ok(result);
	}

	// 찜 삭제
	@DeleteMapping("/{userID}/delete/{bbsID}")
	public ResponseEntity<String> deleteJjim(@PathVariable String userID, @PathVariable int bbsID) {
		String result = jjimService.deleteJjim(userID, bbsID);
		return ResponseEntity.ok(result);
	}

	// 사용자별 찜 게시물 수 조회
	@GetMapping("/{userID}/total-count")
	public ResponseEntity<Integer> getTotalCount(@PathVariable String userID) {
		int totalCount = jjimService.getTotalCount(userID);
		return ResponseEntity.ok(totalCount);
	}

	// 다음 페이지 여부 확인
	@GetMapping("/{userID}/has-next-page")
	public ResponseEntity<Boolean> hasNextPage(@PathVariable String userID, @RequestParam int pageNumber) {
		boolean hasNextPage = jjimService.hasNextPage(userID, pageNumber);
		return ResponseEntity.ok(hasNextPage);
	}
}
