package com.github.hong0805.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
	private String userID;
	private String userPassword;
	private String userEmail;
	private String userName;
}