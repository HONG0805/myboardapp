package com.github.hong0805.service;

import com.github.hong0805.domain.Bbs;
import com.github.hong0805.domain.Reply;
import com.github.hong0805.domain.User;
import com.github.hong0805.exception.ReplyNotFoundException;
import com.github.hong0805.exception.UnauthorizedReplyAccessException;
import com.github.hong0805.repository.BbsRepository;
import com.github.hong0805.repository.ReplyRepository;
import com.github.hong0805.repository.UserRepository;
import com.github.hong0805.web.dto.reply.ReplyRequest;
import com.github.hong0805.web.dto.reply.ReplyResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final BbsRepository bbsRepository;
	private final UserRepository userRepository;

	@Transactional
	public ReplyResponse createReply(ReplyRequest request, String userId) {
		Bbs bbs = bbsRepository.findById(request.getBbsID()).orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 없음"));

		Reply reply = Reply.builder().bbs(bbs).user(user).replyContent(request.getReplyContent()).replyAvailable(true)
				.build();

		Reply saved = replyRepository.save(reply);
		return toDto(saved);
	}

	@Transactional(readOnly = true)
	public List<ReplyResponse> getReplies(Long bbsID) {
		Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
		return replyRepository.findByBbsAndReplyAvailableTrue(bbs).stream().map(this::toDto)
				.collect(Collectors.toList());
	}

	@Transactional
	public ReplyResponse updateReply(Long replyId, String newContent, String userId) {
		Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new ReplyNotFoundException(replyId));

		if (!reply.getUser().getUserId().equals(userId)) {
			throw new UnauthorizedReplyAccessException(userId);
		}

		reply.setReplyContent(newContent);
		return toDto(reply);
	}

	@Transactional
	public void deleteReply(Long replyID, String userId) {
		Reply reply = replyRepository.findById(replyID).orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

		if (!reply.getUser().getUserId().equals(userId)) {
			throw new IllegalStateException("댓글 삭제 권한이 없습니다");
		}

		reply.setReplyAvailable(false);
	}

	private ReplyResponse toDto(Reply reply) {
		return ReplyResponse.builder().replyID(reply.getReplyID()).replyContent(reply.getReplyContent())
				.userID(reply.getUser().getUserId()).replyDate(reply.getReplyDate()).build();
	}

	@Transactional(readOnly = true)
	public Page<ReplyResponse> getRepliesByBbs(Bbs bbs, Pageable pageable) {
		return replyRepository.findByBbs(bbs, pageable).map(this::toDto);
	}
}
