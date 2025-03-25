package com.github.hong0805.chat;

import com.github.hong0805.bbs.BbsRepository;
import com.github.hong0805.chat.dto.request.*;
import com.github.hong0805.chat.dto.response.*;
import com.github.hong0805.chat.ChatRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final BbsRepository bbsRepository;

	@Override
	@Transactional
	public ChatRoomResponse createOrGetChatRoom(ChatRoomCreateRequest request) {
		// 1. 기존 채팅방 검색
		Optional<ChatRoom> existingRoom = chatRoomRepository.findByBbsIdAndUser(request.getBbsId(),
				request.getUserId());

		if (existingRoom.isPresent()) {
			return ChatRoomResponse.from(existingRoom.get());
		}

		// 2. 새 채팅방 생성
		String bbsAuthorId = bbsRepository.findById(request.getBbsId())
				.orElseThrow(() -> new ChatRoomNotFoundException("게시글을 찾을 수 없습니다")).getUserID();

		ChatRoom newRoom = ChatRoom.builder().bbsId(request.getBbsId()).user1Id(request.getUserId())
				.user2Id(bbsAuthorId).build();

		ChatRoom savedRoom = chatRoomRepository.save(newRoom);
		return ChatRoomResponse.from(savedRoom);
	}

	@Override
	@Transactional
	public MessageResponse sendMessage(MessageSendRequest request) {
		ChatRoom room = chatRoomRepository.findById(request.getRoomId())
				.orElseThrow(() -> new ChatRoomNotFoundException("채팅방을 찾을 수 없습니다"));

		ChatMessage message = ChatMessage.builder().room(room).senderId(request.getSenderId())
				.content(HtmlEscapeUtil.escapeHtml(request.getContent())).build();

		ChatMessage savedMessage = chatMessageRepository.save(message);
		return MessageResponse.from(savedMessage, true);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MessageResponse> getMessages(Long roomId, String currentUserId) {
		return chatMessageRepository.findByRoomId(roomId).stream()
				.map(message -> MessageResponse.from(message, message.getSenderId().equals(currentUserId)))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<RoomListResponse> getUserChatRooms(String userId) {
		return chatRoomRepository.findAllByUserId(userId).stream().map(room -> {
			String partnerId = room.getUser1Id().equals(userId) ? room.getUser2Id() : room.getUser1Id();
			return RoomListResponse.from(room, partnerId);
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public Long getBbsIdByRoomId(Long roomId) {
		return chatRoomRepository.findById(roomId).orElseThrow(() -> new ChatRoomNotFoundException("채팅방을 찾을 수 없습니다"))
				.getBbsId();
	}
}