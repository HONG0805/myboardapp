package com.github.hong0805.chat.dto.response;

import com.github.hong0805.chat.ChatMessage;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MessageResponse {
	private final Long messageId;
	private final Long roomId;
	private final String senderId;
	private final String content;
	private final String sentAt;
	private final boolean isMine;

	private MessageResponse(Long messageId, Long roomId, String senderId, String content, String sentAt,
			boolean isMine) {
		this.messageId = messageId;
		this.roomId = roomId;
		this.senderId = senderId;
		this.content = content;
		this.sentAt = sentAt;
		this.isMine = isMine;
	}

	public static MessageResponse from(ChatMessage message, boolean isMine) {
		return new MessageResponse(message.getId(), message.getRoom().getId(), message.getSenderId(),
				message.getContent(), message.getSentAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
				isMine);
	}
}