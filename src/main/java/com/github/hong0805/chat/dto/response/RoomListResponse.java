package com.github.hong0805.chat.dto.response;

import com.github.hong0805.chat.ChatRoom;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class RoomListResponse {
	private final Long roomId;
	private final String partnerId;
	private final Long bbsId;
	private final String createdAt;
	private final String lastMessage;
	private final String lastMessageTime;

	private RoomListResponse(Long roomId, String partnerId, Long bbsId, String createdAt, String lastMessage,
			String lastMessageTime) {
		this.roomId = roomId;
		this.partnerId = partnerId;
		this.bbsId = bbsId;
		this.createdAt = createdAt;
		this.lastMessage = lastMessage;
		this.lastMessageTime = lastMessageTime;
	}

	public static RoomListResponse from(ChatRoom room, String partnerId) {
		return new RoomListResponse(room.getId(), partnerId, room.getBbsId(),
				room.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE), null, null);
	}

	public static RoomListResponse withLastMessage(ChatRoom room, String partnerId, String lastMessage,
			String lastMessageTime) {
		return new RoomListResponse(room.getId(), partnerId, room.getBbsId(),
				room.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE), lastMessage, lastMessageTime);
	}
}