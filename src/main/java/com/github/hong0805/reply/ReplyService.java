package com.github.hong0805.reply;

import com.github.hong0805.reply.dto.request.*;
import com.github.hong0805.reply.dto.response.*;
import org.springframework.data.domain.Page;

public interface ReplyService {
	ReplyResponse createReply(ReplyCreateRequest request);

	Page<ReplyResponse> getReplies(Long bbsId, int page);

	ReplyResponse getReply(Long replyId);

	void deleteReply(Long replyId);

	ReplyResponse updateReply(Long replyId, ReplyUpdateRequest request);
}