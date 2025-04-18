package com.github.hong0805.web.dto.jjim;

import com.github.hong0805.domain.Jjim;
import lombok.Getter;

@Getter
public class JjimResponse {

	private Long jjimID;
	private Long bbsId;
	private String userId;
	private String jjimDate;

	public JjimResponse(Jjim jjim) {
		this.jjimID = jjim.getJjimID();
		this.bbsId = jjim.getBbs().getBbsID();
		this.userId = jjim.getUser().getUserId();
		this.jjimDate = jjim.getJjimDate().toString();
	}
}
