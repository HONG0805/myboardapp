package com.github.hong0805.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
	private String userEmail;
	private String newPassword;
}
