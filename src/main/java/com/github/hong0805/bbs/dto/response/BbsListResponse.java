package com.github.hong0805.bbs.dto.response;

import com.github.hong0805.bbs.Bbs;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class BbsListResponse {
	private final Long bbsID;
	private final String title;
	private final String userID;
	private final String date;
	private final int cost;

	public BbsListResponse(Long bbsID, String title, String userID, String date, int cost) {
		this.bbsID = bbsID;
		this.title = title;
		this.userID = userID;
		this.date = date;
		this.cost = cost;
	}

	public static BbsListResponse from(Bbs bbs) {
		return new BbsListResponse(bbs.getBbsID(), bbs.getBbsTitle(), bbs.getUserID(),
				bbs.getBbsDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), bbs.getCost());
	}
}