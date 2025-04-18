package com.github.hong0805.web.api;

import com.github.hong0805.security.JwtTokenProvider;
import com.github.hong0805.service.ReplyService;
import com.github.hong0805.web.dto.reply.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/replies")
@RequiredArgsConstructor
public class ReplyApiController {

	private final ReplyService replyService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping
	public ResponseEntity<ReplyResponse> createReply(@RequestBody ReplyRequest request,
			HttpServletRequest httpRequest) {

		String userId = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(httpRequest));

		return ResponseEntity.ok(replyService.createReply(request, userId));
	}

	@GetMapping("/{bbsID}")
	public ResponseEntity<List<ReplyResponse>> getReplies(@PathVariable Long bbsID) {
		return ResponseEntity.ok(replyService.getReplies(bbsID));
	}

	@PatchMapping("/{replyID}")
	public ResponseEntity<ReplyResponse> updateReply(@PathVariable Long replyID,
			@RequestBody @Valid ReplyUpdateRequest request, HttpServletRequest httpRequest) {

		String userId = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(httpRequest));

		return ResponseEntity.ok(replyService.updateReply(replyID, request.getNewContent(), userId));
	}

	@DeleteMapping("/{replyID}")
	public ResponseEntity<Void> deleteReply(@PathVariable Long replyID, HttpServletRequest httpRequest) {

		String userId = jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(httpRequest));

		replyService.deleteReply(replyID, userId);
		return ResponseEntity.ok().build();
	}
}