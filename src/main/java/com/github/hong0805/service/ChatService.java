package com.github.hong0805.service;

import com.github.hong0805.domain.ChatRoom;
import com.github.hong0805.domain.Message;
import com.github.hong0805.repository.ChatRoomRepository;
import com.github.hong0805.repository.MessageRepository;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final MessageRepository messageRepository;
	private final BbsService bbsService;
	private final AuthService authService;

	// 채팅방 조회 및 생성
	@Transactional
	public ChatRoomResponse createOrGetChatRoom(String currentUserId, String otherUserId, Long bbsId) {
		BbsResponse post = bbsService.getPostById(bbsId);
		String postAuthorId = post.getUserID();

		if (currentUserId.equals(postAuthorId)) {
			throw new IllegalArgumentException("자신의 게시물에는 채팅할 수 없습니다.");
		}

		Optional<ChatRoom> optionalRoom = chatRoomRepository.findByUser1IdAndUser2IdAndBbsID(currentUserId,
				postAuthorId, bbsId);

		if (!optionalRoom.isPresent()) {
			optionalRoom = chatRoomRepository.findByUser1IdAndUser2IdAndBbsID(postAuthorId, currentUserId, bbsId);
		}

		ChatRoom room = optionalRoom.orElseGet(() -> createNewChatRoom(currentUserId, postAuthorId, bbsId));

		ChatRoomResponse response = convertToChatRoomResponse(room, currentUserId);
		response.setNewRoom(room.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1)));
		return response;
	}

	private ChatRoom createNewChatRoom(String buyerId, String sellerId, Long bbsId) {
		ChatRoom newRoom = new ChatRoom();
		newRoom.setUser1Id(buyerId);
		newRoom.setUser2Id(sellerId);
		newRoom.setBbsID(bbsId);
		newRoom.setCreatedAt(LocalDateTime.now());
		return chatRoomRepository.save(newRoom);
	}

	// 메시지 전송
	@Transactional
	public ChatMessageResponse sendMessage(ChatMessageRequest request, String senderId) {
		ChatRoom room = chatRoomRepository.findById(request.getRoomId())
				.orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

		if (!isRoomParticipant(room, senderId)) {
			throw new IllegalArgumentException("채팅방에 접근할 수 없습니다.");
		}

		Message message = Message.builder().chatRoom(room).userID(senderId).content(request.getContent())
				.createdAt(LocalDateTime.now()).build();

		message = messageRepository.save(message);
		return convertToMessageResponse(message);
	}

	private boolean isRoomParticipant(ChatRoom room, String userId) {
		return room.getUser1Id().equals(userId) || room.getUser2Id().equals(userId);
	}

	// 채팅 기록 조회
	@Transactional(readOnly = true)
	public List<ChatMessageResponse> getMessages(Long roomId, String userId) {
		ChatRoom room = chatRoomRepository.findById(roomId)
				.orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

		if (!isRoomParticipant(room, userId)) {
			throw new IllegalArgumentException("접근 권한이 없습니다.");
		}

		return messageRepository.findByChatRoom_RoomIDOrderByCreatedAtAsc(roomId).stream()
				.map(this::convertToMessageResponse).collect(Collectors.toList());
	}

	// 사용자 채팅방 목록 조회
	@Transactional(readOnly = true)
	public List<ChatRoomResponse> getUserChatRooms(String userId) {
		return chatRoomRepository.findByUser1IdOrUser2IdOrderByCreatedAtDesc(userId, userId).stream()
				.map(room -> convertToChatRoomResponse(room, userId)).collect(Collectors.toList());
	}

	// 메시지 읽음 표시
	@Transactional
	public void markMessagesAsRead(Long roomId, String userId) {
		messageRepository.markAsRead(roomId, userId);
	}

	// DTO 반환 메서드
	private ChatMessageResponse convertToMessageResponse(Message message) {
		return ChatMessageResponse.builder().messageId(message.getMessageID()).roomId(message.getChatRoom().getRoomID())
				.userId(message.getUserID()).content(message.getContent()).createdAt(message.getCreatedAt()).build();
	}

	private ChatRoomResponse convertToChatRoomResponse(ChatRoom room, String currentUserId) {
		if (room == null) {
			throw new IllegalArgumentException("채팅방 정보가 없습니다.");
		}

		// 상대방 정보 설정
		String otherUserId = room.getUser1Id().equals(currentUserId) ? room.getUser2Id() : room.getUser1Id();

		// 게시물 정보 조회
		BbsResponse bbs = bbsService.getPostById(room.getBbsID());

		return ChatRoomResponse.builder().roomId(room.getRoomID()).bbsId(room.getBbsID())
				.bbsTitle(bbs != null ? bbs.getBbsTitle() : "삭제된 게시물").otherUserId(otherUserId)
				.otherUserName(getSafeUserName(otherUserId)).createdAt(room.getCreatedAt())
				.lastMessage(getLastMessageContent(room)).lastMessageTime(getLastMessageTime(room))
				.unreadCount(getUnreadCount(room.getRoomID(), currentUserId)).build();
	}

	private String getSafeUserName(String userId) {
		try {
			String name = authService.getUserName(userId);
			return name != null ? name : "알 수 없음";
		} catch (Exception e) {
			return "알 수 없음";
		}
	}

	private String getLastMessageContent(ChatRoom room) {
		return room.getMessages() != null && !room.getMessages().isEmpty()
				? room.getMessages().get(room.getMessages().size() - 1).getContent()
				: null;
	}

	private LocalDateTime getLastMessageTime(ChatRoom room) {
		return room.getMessages() != null && !room.getMessages().isEmpty()
				? room.getMessages().get(room.getMessages().size() - 1).getCreatedAt()
				: null;
	}

	private int getUnreadCount(Long roomId, String userId) {
		try {
			return (int) messageRepository.countUnreadMessages(roomId, userId);
		} catch (Exception e) {
			return 0;
		}
	}
}