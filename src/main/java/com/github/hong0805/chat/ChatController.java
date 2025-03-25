package com.github.hong0805.chat;

import com.github.hong0805.chat.ChatService;
import com.github.hong0805.chat.dto.request.*;
import com.github.hong0805.chat.dto.response.*;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@PostMapping("/rooms")
	public ResponseEntity<ChatRoomResponse> createRoom(@RequestBody @Valid ChatRoomCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createOrGetChatRoom(request));
	}

	@PostMapping("/messages")
	public ResponseEntity<MessageResponse> sendMessage(@RequestBody @Valid MessageSendRequest request) {
		return ResponseEntity.ok(chatService.sendMessage(request));
	}

	@GetMapping("/rooms/{roomId}/messages")
	public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable Long roomId,
			@RequestParam String currentUserId) {
		return ResponseEntity.ok(chatService.getMessages(roomId, currentUserId));
	}

	@GetMapping("/users/{userId}/rooms")
	public ResponseEntity<List<RoomListResponse>> getUserRooms(@PathVariable String userId) {
		return ResponseEntity.ok(chatService.getUserChatRooms(userId));
	}

	@GetMapping("/rooms/{roomId}/bbs")
	public ResponseEntity<Long> getBbsIdByRoomId(@PathVariable Long roomId) {
		return ResponseEntity.ok(chatService.getBbsIdByRoomId(roomId));
	}
}