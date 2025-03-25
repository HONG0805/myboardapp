package com.github.hong0805.reply.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyCreateRequest {
	@NotNull
	private Long bbsId;

	@NotBlank
	@Size(max = 1000)
	private String content;

	@NotBlank
	private String userId;
}