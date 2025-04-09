package com.github.hong0805.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
	private boolean success;
	private String message;
}
