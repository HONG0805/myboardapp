package com.github.hong0805.chat;

import com.github.hong0805.chat.dto.request.*;
import com.github.hong0805.chat.dto.response.*;

import java.util.List;

public interface ChatService {
	ChatRoomResponse createOrGetChatRoom(ChatRoomCreateRequest request);

	MessageResponse sendMessage(MessageSendRequest request);

	List<MessageResponse> getMessages(Long roomId, String currentUserId);

	List<RoomListResponse> getUserChatRooms(String userId);

	Long getBbsIdByRoomId(Long roomId);
}