package com.github.hong0805.web.dto.reply;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyUpdateRequest {
	@NotBlank
	private String newContent;
}
