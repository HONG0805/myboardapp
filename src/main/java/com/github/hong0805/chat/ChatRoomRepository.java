package com.github.hong0805.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
	// user1_id 또는 user2_id가 주어진 userID와 일치하는 채팅방을 찾아 반환
	List<ChatRoom> findByUser1IDOrUser2ID(String user1ID, String user2ID);

	// 게시물 ID와 사용자 ID로 기존 채팅방을 찾는 메서드 (작성된 쿼리와 관련된 메서드)
	List<ChatRoom> findByBbsIDAndUser1IDOrUser2ID(int bbsID, String user1ID, String user2ID);
	

}
