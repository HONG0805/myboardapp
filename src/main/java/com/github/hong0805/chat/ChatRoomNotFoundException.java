package com.github.hong0805.chat;

public class ChatRoomNotFoundException extends RuntimeException {
	public ChatRoomNotFoundException(String message) {
		super(message);
	}
}