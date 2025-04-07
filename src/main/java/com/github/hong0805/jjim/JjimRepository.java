package com.github.hong0805.jjim;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface JjimRepository extends JpaRepository<Jjim, Integer> {

	boolean existsByUserIDAndBbsID(String userID, int bbsID);

	Page<Jjim> findByUserID(String userID, Pageable pageable);

	@Modifying
	@Transactional
	@Query("DELETE FROM Jjim j WHERE j.userID = :userID AND j.bbsID = :bbsID")
	int deleteByUserIDAndBbsID(String userID, int bbsID);

	@Query("SELECT COUNT(j) FROM Jjim j WHERE j.userID = :userID")
	long countByUserID(String userID);
}