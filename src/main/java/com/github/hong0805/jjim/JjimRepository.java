package com.github.hong0805.jjim;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface JjimRepository extends JpaRepository<Jjim, Integer> {
	// 이미 찜한 게시물 확인
	boolean existsByUserIDAndBbsID(String userID, int bbsID);

	// 사용자별 찜 리스트 조회
	List<Jjim> findByUserID(String userID);

	// 게시물 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM Jjim jj WHERE jj.userID = :userID AND jj.bbsID = :bbsID")
	void deleteByUserIDAndBbsID(String userID, int bbsID);
}
