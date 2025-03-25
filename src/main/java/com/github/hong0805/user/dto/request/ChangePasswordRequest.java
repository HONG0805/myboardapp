package com.github.hong0805.user.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
	@NotBlank
	private String id;
	@NotBlank
	private String currentPassword;
	@NotBlank
	private String newPassword;
}
