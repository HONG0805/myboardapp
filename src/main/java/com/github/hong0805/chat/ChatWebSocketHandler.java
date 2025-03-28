package com.github.hong0805.chat;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws WebSocketException {
		String payload = message.getPayload();
		// 메시지 처리 로직 (예: DB에 저장, 다른 클라이언트에 메시지 전송)
		System.out.println("Received message: " + payload);

		// 메시지를 받은 클라이언트에 다시 메시지 전송
		try {
			session.sendMessage(new TextMessage("Echo: " + payload)); // 클라이언트에게 에코 메시지 전송
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 연결이 성립되었을 때 처리할 로직 (예: 세션 정보 저장)
		System.out.println("WebSocket connection established: " + session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 연결 종료시 처리할 로직 (예: 세션 삭제)
		System.out.println("WebSocket connection closed: " + session.getId());
	}
}
