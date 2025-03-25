package com.github.hong0805.reply;

import com.github.hong0805.reply.Reply;
import com.github.hong0805.reply.ReplyRepository;
import com.github.hong0805.reply.dto.request.*;
import com.github.hong0805.reply.dto.response.*;
import com.github.hong0805.reply.ReplyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

	private final ReplyRepository replyRepository;

	@Override
	@Transactional
	public ReplyResponse createReply(ReplyCreateRequest request) {
		Reply reply = Reply.builder().bbsId(request.getBbsId()).content(request.getContent())
				.userId(request.getUserId()).build();

		Reply savedReply = replyRepository.save(reply);
		return ReplyResponse.from(savedReply);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ReplyResponse> getReplies(Long bbsId, int page) {
		PageRequest pageable = PageRequest.of(page - 1, 10);
		return replyRepository.findByBbsIdAndAvailableTrueOrderByIdDesc(bbsId, pageable).map(ReplyResponse::from);
	}

	@Override
	@Transactional(readOnly = true)
	public ReplyResponse getReply(Long replyId) {
		Reply reply = replyRepository.findById(replyId)
				.orElseThrow(() -> new ReplyNotFoundException("Reply not found"));
		return ReplyResponse.from(reply);
	}

	@Override
	@Transactional
	public void deleteReply(Long replyId) {
		replyRepository.deactivateById(replyId);
	}

	@Override
	@Transactional
	public ReplyResponse updateReply(Long replyId, ReplyUpdateRequest request) {
		int updated = replyRepository.updateContent(replyId, request.getContent());
		if (updated == 0) {
			throw new ReplyNotFoundException("Reply not found");
		}
		return getReply(replyId); // 업데이트 후 조회
	}
}