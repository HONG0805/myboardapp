package com.github.hong0805.bbs.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BbsSearchRequest {
	private String keyword;
	private int page;

	public BbsSearchRequest(String keyword, int page) {
		this.keyword = keyword;
		this.page = page;
	}
}