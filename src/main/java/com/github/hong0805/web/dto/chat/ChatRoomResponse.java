package com.github.hong0805.web.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatRoomResponse {
	private Long roomId;
	private Long bbsId;
	private String bbsTitle;
	private String otherUserId;
	private String otherUserName;
	private LocalDateTime createdAt;
	private int unreadCount;
	private String lastMessage;
	private LocalDateTime lastMessageTime;
	private boolean newRoom;
}