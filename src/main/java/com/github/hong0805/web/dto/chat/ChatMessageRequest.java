package com.github.hong0805.web.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMessageRequest {
	private Long roomId;
	private String content;
	private String senderId;
	private String type;
}