package com.github.hong0805.reply;

import com.github.hong0805.reply.ReplyService;
import com.github.hong0805.reply.dto.request.*;
import com.github.hong0805.reply.dto.response.*;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping
	public ResponseEntity<ReplyResponse> createReply(@RequestBody @Valid ReplyCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(replyService.createReply(request));
	}

	@GetMapping("/bbs/{bbsId}")
	public ResponseEntity<Page<ReplyResponse>> getReplies(@PathVariable Long bbsId,
			@RequestParam(defaultValue = "1") int page) {
		return ResponseEntity.ok(replyService.getReplies(bbsId, page));
	}

	@GetMapping("/{replyId}")
	public ResponseEntity<ReplyResponse> getReply(@PathVariable Long replyId) {
		return ResponseEntity.ok(replyService.getReply(replyId));
	}

	@DeleteMapping("/{replyId}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
		replyService.deleteReply(replyId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{replyId}")
	public ResponseEntity<ReplyResponse> updateReply(@PathVariable Long replyId,
			@RequestBody @Valid ReplyUpdateRequest request) {
		return ResponseEntity.ok(replyService.updateReply(replyId, request));
	}
}