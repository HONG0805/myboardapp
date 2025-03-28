package com.github.hong0805.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private MessageRepository messageRepository;

	// 채팅방 생성 또는 기존 채팅방 반환
	public int createChatRoom(int bbsID, String userID) {
		// 기존 채팅방이 있는지 확인
		List<ChatRoom> existingRooms = chatRoomRepository.findByBbsIDAndUser1IDOrUser2ID(bbsID, userID, userID);
		if (!existingRooms.isEmpty()) {
			// 이미 존재하는 채팅방이 있으면 해당 roomID 반환
			return existingRooms.get(0).getRoomID();
		}

		// 새 채팅방 생성
		ChatRoom newRoom = new ChatRoom();
		newRoom.setBbsID(bbsID);
		newRoom.setUser1ID(userID);

		// 게시물 작성자의 userID를 가져옴
		String bbsUserID = getBbsUserID(bbsID);
		if (bbsUserID == null) {
			return -1; // 게시물 작성자 ID를 찾을 수 없으면 채팅방을 만들지 않음
		}
		newRoom.setUser2ID(bbsUserID);

		// 채팅방 저장
		chatRoomRepository.save(newRoom);
		return newRoom.getRoomID();
	}

	// 게시물 작성자의 userID를 가져오는 메서드 (여기서는 예시로 반환)
	private String getBbsUserID(int bbsID) {
		// 실제 구현에서는 게시물 ID로 게시물 작성자의 ID를 조회하는 로직 필요
		// 여기서는 임시로 "someUser"를 반환
		return "someUser";
	}

	// 채팅방의 메시지 조회
	public List<Message> getMessages(int roomID) {
		return messageRepository.findByRoomIDOrderByCreatedAtAsc(roomID);
	}

	// 메시지 전송
	public String sendMessage(int roomID, String userID, String message) {
		// 새 메시지 객체 생성
		Message newMessage = new Message();
		newMessage.setRoomID(roomID);
		newMessage.setUserID(userID);
		newMessage.setMessage(message);
		newMessage.setCreatedAt(java.time.LocalDateTime.now());

		// 메시지 저장
		messageRepository.save(newMessage);

		// 메시지의 생성 시간 반환
		return newMessage.getCreatedAt().toString();
	}

	// 채팅방에 참여한 사용자 목록과 상대방 ID 가져오기
	public List<ChatRoom> getJoinedRoomsWithPartner(String userID) {
		List<ChatRoom> chatRooms = chatRoomRepository.findByUser1IDOrUser2ID(userID, userID);
		List<ChatRoom> result = new ArrayList<>();

		for (ChatRoom room : chatRooms) {
			// 채팅방에 참여한 상대방 ID를 찾기
			String partnerID = room.getUser1ID().equals(userID) ? room.getUser2ID() : room.getUser1ID();

			// 상대방 ID를 함께 저장
			room.setPartnerID(partnerID);
			result.add(room);
		}
		return result;
	}

}
