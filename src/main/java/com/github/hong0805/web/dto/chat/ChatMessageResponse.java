package com.github.hong0805.web.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessageResponse {
	private Long messageId;
	private Long roomId;
	private String userId;
	private String content;
	private LocalDateTime createdAt;
	private boolean isRead;
}