package com.github.hong0805.web.api;

import com.github.hong0805.service.ChatService;
import com.github.hong0805.web.dto.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatApiController {

	private final ChatService chatService;

	@GetMapping("/rooms")
	public ResponseEntity<List<ChatRoomResponse>> getUserChatRooms(Authentication authentication) {
		String userId = authentication.getName();
		return ResponseEntity.ok(chatService.getUserChatRooms(userId));
	}

	@GetMapping("/messages/{roomId}")
	public ResponseEntity<List<ChatMessageResponse>> getMessages(@PathVariable Long roomId,
			Authentication authentication) {
		String userId = authentication.getName();
		return ResponseEntity.ok(chatService.getMessages(roomId, userId));
	}

	@PostMapping("/send")
	public ResponseEntity<ChatMessageResponse> sendMessage(@RequestBody ChatMessageRequest request,
			Authentication authentication) {
		String senderId = authentication.getName();
		return ResponseEntity.ok(chatService.sendMessage(request, senderId));
	}

	@PostMapping("/create-room")
	public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestParam String otherUserId, @RequestParam Long bbsId,
			Authentication authentication) {
		String userId = authentication.getName();
		return ResponseEntity.ok(chatService.createOrGetChatRoom(userId, otherUserId, bbsId));
	}
}