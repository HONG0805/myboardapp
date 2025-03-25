package com.github.hong0805.bbs.dto.response;

import com.github.hong0805.bbs.Bbs;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BbsDetailResponse {
	private final Long bbsID;
	private final String title;
	private final String userID;
	private final String content;
	private final String date;
	private final int cost;

	public BbsDetailResponse(Long bbsID, String title, String userID, String content, String date, int cost) {
		this.bbsID = bbsID;
		this.title = title;
		this.userID = userID;
		this.content = content;
		this.date = date;
		this.cost = cost;
	}

	public static BbsDetailResponse from(Bbs bbs) {
		return new BbsDetailResponse(bbs.getBbsID(), bbs.getBbsTitle(), bbs.getUserID(), bbs.getBbsContent(),
				bbs.getBbsDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), bbs.getCost());
	}
}