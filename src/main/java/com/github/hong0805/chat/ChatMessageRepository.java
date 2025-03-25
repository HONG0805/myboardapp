package com.github.hong0805.chat;

import com.github.hong0805.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
	@Query("SELECT m FROM ChatMessage m WHERE m.room.id = :roomId ORDER BY m.sentAt ASC")
	List<ChatMessage> findByRoomId(@Param("roomId") Long roomId);
}
