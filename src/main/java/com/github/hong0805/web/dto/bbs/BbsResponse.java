package com.github.hong0805.web.dto.bbs;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BbsResponse {

	private Long bbsID;
	private String bbsTitle;
	private String userID;
	private String userName;
	private String bbsContent;
	private LocalDateTime bbsDate;
	private boolean bbsAvailable;
	private int cost;
	private String bbsImage;

}
