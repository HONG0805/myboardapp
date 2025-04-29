package com.github.hong0805.web.view;

import com.github.hong0805.service.ChatService;
import com.github.hong0805.web.dto.chat.ChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatViewController {

	private final ChatService chatService;

	@GetMapping("/list")
	public String chatList(Authentication authentication, Model model) {
		String userId = authentication.getName();
		model.addAttribute("chatRooms", chatService.getUserChatRooms(userId));
		return "chat/list";
	}

	@GetMapping("/room/{roomId}")
	public String chatRoom(@PathVariable Long roomId, Authentication authentication, Model model) {
		String userId = authentication.getName();
		model.addAttribute("roomId", roomId);
		model.addAttribute("messages", chatService.getMessages(roomId, userId));
		return "chat/room";
	}
}