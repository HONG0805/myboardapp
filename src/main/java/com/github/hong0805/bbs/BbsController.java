package com.github.hong0805.bbs;

import com.github.hong0805.bbs.dto.request.*;
import com.github.hong0805.bbs.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bbs")
public class BbsController {

	private final BbsService bbsService;

	@PostMapping
	public ResponseEntity<BbsDetailResponse> createBbs(@RequestBody BbsCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bbsService.createBbs(request));
	}

	@GetMapping("/{bbsID}")
	public ResponseEntity<BbsDetailResponse> getBbs(@PathVariable Long bbsID) {
		return ResponseEntity.ok(bbsService.getBbs(bbsID));
	}

	@GetMapping
	public ResponseEntity<Page<BbsListResponse>> getBbsList(@RequestParam(defaultValue = "1") int page) {
		return ResponseEntity.ok(bbsService.getBbsList(page));
	}

	@GetMapping("/search")
	public ResponseEntity<Page<BbsListResponse>> searchBbs(@RequestParam String keyword,
			@RequestParam(defaultValue = "1") int page) {
		BbsSearchRequest request = new BbsSearchRequest(keyword, page);
		return ResponseEntity.ok(bbsService.searchBbs(request));
	}

	@PutMapping("/{bbsID}")
	public ResponseEntity<BbsDetailResponse> updateBbs(@PathVariable Long bbsID,
			@RequestBody BbsUpdateRequest request) {
		return ResponseEntity.ok(bbsService.updateBbs(bbsID, request));
	}

	@DeleteMapping("/{bbsID}")
	public ResponseEntity<Void> deleteBbs(@PathVariable Long bbsID) {
		bbsService.deleteBbs(bbsID);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(BbsNotFoundException.class)
	public ResponseEntity<String> handleBbsNotFound(BbsNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}
}