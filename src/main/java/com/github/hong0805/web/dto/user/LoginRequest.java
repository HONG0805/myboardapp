package com.github.hong0805.web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String userId;
	private String userPassword;
}
