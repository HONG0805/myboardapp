package com.github.hong0805.reply.dto.response;

import com.github.hong0805.reply.Reply;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ReplyResponse {
	private final Long id;
	private final Long bbsId;
	private final String content;
	private final String userId;
	private final String createdAt;

	public ReplyResponse(Long id, Long bbsId, String content, String userId, String createdAt) {
		this.id = id;
		this.bbsId = bbsId;
		this.content = content;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public static ReplyResponse from(Reply reply) {
		return new ReplyResponse(reply.getId(), reply.getBbsId(), reply.getContent(), reply.getUserId(),
				reply.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}