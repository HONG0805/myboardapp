package com.github.hong0805.repository;

import com.github.hong0805.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findByChatRoom_RoomIDOrderByCreatedAtAsc(Long roomId);

	@Query("SELECT m FROM Message m WHERE m.chatRoom.roomID = :roomId AND m.userID != :userId AND m.isRead = false")
	List<Message> findUnreadMessages(Long roomId, String userId);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Message m SET m.isRead = true WHERE m.chatRoom.roomID = :roomId AND m.userID != :userId AND m.isRead = false")
	void markAsRead(Long roomId, String userId);

	@Query("SELECT COUNT(m) FROM Message m WHERE m.chatRoom.roomID = :roomId AND m.userID != :userId AND m.isRead = false")
	long countUnreadMessages(@Param("roomId") Long roomId, @Param("userId") String userId);


}