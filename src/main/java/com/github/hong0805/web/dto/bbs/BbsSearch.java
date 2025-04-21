package com.github.hong0805.web.dto.bbs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BbsSearch {
	private String keyword;
	private String searchType;

	public BbsSearch(String keyword) {
		this.keyword = keyword;
	}

}
