package com.github.hong0805.bbs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BbsRepository extends JpaRepository<Bbs, Integer> {
	// 사용자별 찜한 게시물 조회
	@Query("SELECT b FROM Bbs b WHERE b.bbsID IN (SELECT jj.bbsID FROM Jjim jj WHERE jj.userID = :userID) ORDER BY b.bbsID DESC")
	List<Bbs> findJjimListByUserID(@Param("userID") String userID, int offset, int limit);
}
