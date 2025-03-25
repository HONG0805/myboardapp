package com.github.hong0805.user.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
	@NotBlank
	private String id;
	@NotBlank
	private String email;
	@NotBlank
	private String newPassword;
}