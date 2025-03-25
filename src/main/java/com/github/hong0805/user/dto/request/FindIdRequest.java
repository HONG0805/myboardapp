package com.github.hong0805.user.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String name;
}