package com.github.hong0805.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	// 채팅방 생성 (게시물 ID와 사용자 ID를 받아서 채팅방 생성)
	@PostMapping("/create")
	public ResponseEntity<Integer> createChatRoom(@RequestParam int bbsID, @RequestParam String userID) {
		int roomID = chatService.createChatRoom(bbsID, userID);
		if (roomID == -1) {
			return ResponseEntity.status(400).body(-1); // 오류 발생 시 -1 반환
		}
		return ResponseEntity.ok(roomID);
	}

	// 채팅방 메시지 조회 (채팅방 ID를 받아서 해당 메시지 리스트 반환)
	@GetMapping("/messages/{roomID}")
	public ResponseEntity<List<String>> getMessages(@PathVariable int roomID) {
		List<Message> messages = chatService.getMessages(roomID);
		List<String> formattedMessages = messages.stream().map(msg -> msg.getUserID() + " : " + msg.getMessage())
				.collect(Collectors.toList()); // 여기서 toList() 대신 Collectors.toList() 사용
		return ResponseEntity.ok(formattedMessages);
	}

	// 메시지 전송 (채팅방 ID, 사용자 ID, 메시지를 받아서 전송)
	@PostMapping("/send")
	public ResponseEntity<String> sendMessage(@RequestParam int roomID, @RequestParam String userID,
			@RequestParam String message) {
		String timestamp = chatService.sendMessage(roomID, userID, message);
		return ResponseEntity.ok(timestamp);
	}
}
