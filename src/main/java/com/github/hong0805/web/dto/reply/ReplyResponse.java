package com.github.hong0805.web.dto.reply;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyResponse {
	private Long replyID;
	private String replyContent;
	private String userID;
	private LocalDateTime replyDate;
}