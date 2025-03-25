package com.github.hong0805.chat.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageSendRequest {
	@NotNull(message = "채팅방 ID는 필수입니다")
	private Long roomId;

	@NotBlank(message = "보내는 사람 ID는 필수입니다")
	private String senderId;

	@NotBlank(message = "메시지 내용은 필수입니다")
	private String content;
}