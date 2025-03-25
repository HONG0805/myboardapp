package com.github.hong0805.reply.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyUpdateRequest {
	@NotBlank
	@Size(max = 1000)
	private String content;
}
