package com.github.hong0805.reply;

import com.github.hong0805.bbs.Bbs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	// 댓글 목록 조회 (페이징)
	@GetMapping("/{bbsID}")
	public ResponseEntity<List<Reply>> getReplies(@PathVariable int bbsID,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber) {
		List<Reply> replies = replyService.getReplies(bbsID, pageNumber);
		if (replies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(replies, HttpStatus.OK);
	}

	// 댓글 조회
	@GetMapping("/reply/{replyID}")
	public ResponseEntity<Reply> getReply(@PathVariable int replyID) {
		Reply reply = replyService.getReply(replyID);
		if (reply == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reply, HttpStatus.OK);
	}

	// 댓글 작성
	@PostMapping("/{bbsID}")
	public ResponseEntity<Reply> createReply(@PathVariable int bbsID, @RequestBody Reply reply) {
		reply.setBbs(new Bbs(bbsID));
		Reply newReply = replyService.createReply(reply);
		return new ResponseEntity<>(newReply, HttpStatus.CREATED);
	}

	// 댓글 수정
	@PutMapping("/reply/{replyID}")
	public ResponseEntity<Reply> updateReply(@PathVariable int replyID, @RequestBody String replyContent) {
		Reply updatedReply = replyService.updateReply(replyID, replyContent);
		if (updatedReply == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedReply, HttpStatus.OK);
	}

	// 댓글 삭제
	@DeleteMapping("/reply/{replyID}")
	public ResponseEntity<HttpStatus> deleteReply(@PathVariable int replyID) {
		boolean isDeleted = replyService.deleteReply(replyID);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
