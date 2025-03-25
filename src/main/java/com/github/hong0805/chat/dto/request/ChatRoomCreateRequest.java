package com.github.hong0805.chat.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequest {
	@NotNull(message = "게시글 ID는 필수입니다")
	private Long bbsId;

	@NotNull(message = "사용자 ID는 필수입니다")
	private String userId;
}