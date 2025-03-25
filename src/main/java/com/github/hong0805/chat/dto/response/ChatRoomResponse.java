package com.github.hong0805.chat.dto.response;

import com.github.hong0805.chat.ChatRoom;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChatRoomResponse {
	private final Long roomId;
	private final Long bbsId;
	private final String user1Id;
	private final String user2Id;
	private final String createdAt;

	private ChatRoomResponse(Long roomId, Long bbsId, String user1Id, String user2Id, String createdAt) {
		this.roomId = roomId;
		this.bbsId = bbsId;
		this.user1Id = user1Id;
		this.user2Id = user2Id;
		this.createdAt = createdAt;
	}

	public static ChatRoomResponse from(ChatRoom room) {
		return new ChatRoomResponse(room.getId(), room.getBbsId(), room.getUser1Id(), room.getUser2Id(),
				room.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
}