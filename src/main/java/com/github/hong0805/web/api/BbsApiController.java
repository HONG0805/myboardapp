package com.github.hong0805.web.api;

import com.github.hong0805.service.BbsService;
import com.github.hong0805.web.dto.user.ApiResponse;
import com.github.hong0805.web.dto.bbs.BbsRequest;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/bbs")
@RequiredArgsConstructor
public class BbsApiController {

	private final BbsService bbsService;

	@Value("${bbs.upload-dir}")
	private String uploadDir;

	// 게시글 등록
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse> createPost(@ModelAttribute BbsRequest request,
			@RequestPart(value = "file", required = false) MultipartFile file, Authentication authentication)
			throws IOException {

		// 인증된 사용자의 ID를 가져옵니다.
		String userId = authentication.getName();
		request.setUserID(userId);

		// 게시글 등록
		bbsService.createPost(request, file, userId);

		return ResponseEntity.ok(new ApiResponse(true, "게시글이 등록되었습니다."));
	}

	// 게시글 수정
	@PostMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse> updatePost(@PathVariable Long id,
			@RequestPart(value = "file", required = false) MultipartFile file, @ModelAttribute BbsRequest request)
			throws IOException {

		bbsService.updatePost(id, request, file);
		return ResponseEntity.ok(new ApiResponse(true, "게시글이 수정되었습니다."));
	}

	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long id) {
		try {
			bbsService.deletePost(id);
			return ResponseEntity.ok(new ApiResponse(true, "게시글이 삭제되었습니다."));
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "게시글 삭제 중 오류가 발생했습니다."));
		}
	}

	// 게시글 단일 조회
	@GetMapping("/{id}")
	public ResponseEntity<BbsResponse> getPost(@PathVariable Long id) {
		// 게시글 조회
		return ResponseEntity.ok(bbsService.getPostById(id));
	}

	// 게시글 목록 + 검색 + 페이징
	@GetMapping
	public ResponseEntity<Page<BbsResponse>> getPosts(@ModelAttribute BbsSearch search,
			@PageableDefault(size = 10, sort = "bbsDate", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
		// 게시글 목록 조회
		return ResponseEntity.ok(bbsService.getPostList(search, pageable));
	}

	// 이미지 제공
	@GetMapping("/images/{fileName:.+}")
	public ResponseEntity<byte[]> serveImage(@PathVariable String fileName) throws IOException {
		Path imagePath = Paths.get(uploadDir).resolve(fileName);

		if (!Files.exists(imagePath)) {
			return ResponseEntity.notFound().build();
		}

		byte[] imageBytes = Files.readAllBytes(imagePath);

		String mimeType = Files.probeContentType(imagePath);
		MediaType mediaType = mimeType != null ? MediaType.parseMediaType(mimeType) : MediaType.IMAGE_JPEG;

		return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
	}
}