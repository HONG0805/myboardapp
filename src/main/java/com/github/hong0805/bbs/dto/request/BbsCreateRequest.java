package com.github.hong0805.bbs.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BbsCreateRequest {
	@NotBlank(message = "제목은 필수 입력값입니다.")
	@Size(max = 200, message = "제목은 200자 이내로 입력해주세요.")
	private String title;

	@NotBlank(message = "작성자 ID는 필수 입력값입니다.")
	private String userID;

	@NotBlank(message = "내용은 필수 입력값입니다.")
	private String content;

	@Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
	private int cost;
}