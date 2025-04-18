package com.github.hong0805.web.dto.reply;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequest {
	@NotNull(message = "게시글 ID는 필수입니다")
	private Long bbsID;

	@NotBlank(message = "댓글 내용은 필수입니다")
	@Size(max = 1000, message = "댓글은 1000자 이내로 작성해주세요")
	private String replyContent;
}
