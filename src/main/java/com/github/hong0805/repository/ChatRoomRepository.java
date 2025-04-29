package com.github.hong0805.repository;

import com.github.hong0805.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
	Optional<ChatRoom> findByUser1IdAndUser2IdAndBbsID(String user1Id, String user2Id, Long bbsID);

	List<ChatRoom> findByUser1IdOrUser2IdOrderByCreatedAtDesc(String user1Id, String user2Id);

	boolean existsByRoomIDAndUser1IdOrRoomIDAndUser2Id(Long roomId1, String user1Id, Long roomId2, String user2Id);

	@Query("SELECT r FROM ChatRoom r WHERE ((r.user1Id = :user1 AND r.user2Id = :user2) OR (r.user1Id = :user2 AND r.user2Id = :user1)) AND r.bbsID = :bbsId")
	Optional<ChatRoom> findByUserIdsAndBbsId(String user1, String user2, Long bbsId);

}