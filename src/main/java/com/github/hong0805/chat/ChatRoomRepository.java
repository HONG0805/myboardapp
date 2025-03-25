package com.github.hong0805.chat;

import com.github.hong0805.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	@Query("SELECT cr FROM ChatRoom cr WHERE cr.bbsId = :bbsId AND (cr.user1Id = :userId OR cr.user2Id = :userId)")
	Optional<ChatRoom> findByBbsIdAndUser(@Param("bbsId") Long bbsId, @Param("userId") String userId);

	@Query("SELECT cr FROM ChatRoom cr WHERE cr.user1Id = :userId OR cr.user2Id = :userId")
	List<ChatRoom> findAllByUserId(@Param("userId") String userId);
}