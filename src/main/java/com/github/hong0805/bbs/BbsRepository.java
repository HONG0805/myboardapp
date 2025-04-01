package com.github.hong0805.bbs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BbsRepository extends JpaRepository<Bbs, Integer> {
	// 사용자별 찜한 게시물 조회
	@Query(value = "SELECT * FROM bbs WHERE bbsID IN (SELECT bbsID FROM jjim WHERE userID = :userID) ORDER BY bbsID DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Bbs> findJjimListByUserID(@Param("userID") String userID, @Param("limit") int limit,
			@Param("offset") int offset);

}
