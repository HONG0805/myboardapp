package com.github.hong0805.web.api;

import com.github.hong0805.service.ChatService;
import com.github.hong0805.web.dto.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

	private final ChatService chatService;
	private final SimpMessageSendingOperations messagingTemplate;

	@MessageMapping("/chat.send")
	public void handleMessage(ChatMessageRequest request, Principal principal) {
		String senderId = principal.getName();
		ChatMessageResponse response = chatService.sendMessage(request, senderId);
		messagingTemplate.convertAndSend("/topic/chat/" + request.getRoomId(), response);
	}
}