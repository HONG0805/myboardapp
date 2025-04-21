package com.github.hong0805.web.dto.bbs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BbsRequest {
	private String bbsTitle;
	private String userID;
	private String bbsContent;
	private int cost;
	private String bbsImage;

	public String getBbsImage() {
		return bbsImage;
	}

	public void setBbsImage(String bbsImage) {
		this.bbsImage = bbsImage;
	}
}
