package com.github.hong0805.web.api;

import com.github.hong0805.service.BbsService;
import com.github.hong0805.web.dto.user.ApiResponse;
import com.github.hong0805.web.dto.bbs.BbsRequest;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bbs")
@RequiredArgsConstructor
public class BbsApiController {

	private final BbsService bbsService;

	// 게시글 등록
	@PostMapping
	public ResponseEntity<ApiResponse> createPost(@RequestBody BbsRequest request) {
		bbsService.createPost(request);
		return ResponseEntity.ok(new ApiResponse(true, "게시글이 등록되었습니다."));
	}

	// 게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updatePost(@PathVariable Long id, @RequestBody BbsRequest request) {
		bbsService.updatePost(id, request);
		return ResponseEntity.ok(new ApiResponse(true, "게시글이 수정되었습니다."));
	}

	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id) {
		bbsService.deletePost(id);
		return ResponseEntity.ok(new ApiResponse(true, "게시글이 삭제되었습니다."));
	}

	// 게시글 단일 조회
	@GetMapping("/{id}")
	public ResponseEntity<BbsResponse> getPost(@PathVariable Long id) {
		return ResponseEntity.ok(bbsService.getPostById(id));
	}

	// 게시글 목록 + 검색 + 페이징
	@GetMapping
	public ResponseEntity<Page<BbsResponse>> getPosts(@ModelAttribute BbsSearch search,
			@PageableDefault(size = 10, sort = "bbsDate", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(bbsService.getPostList(search, pageable));
	}
}
