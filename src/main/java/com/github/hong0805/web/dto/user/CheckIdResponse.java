package com.github.hong0805.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckIdResponse {
	private boolean exists;
}
