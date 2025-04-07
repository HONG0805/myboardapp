package com.github.hong0805.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BbsRepository extends JpaRepository<Bbs, Integer> {

	@Query("SELECT b FROM Bbs b WHERE (b.bbsTitle LIKE %:keyword% OR b.bbsContent LIKE %:keyword%) AND b.bbsAvailable = true")
	Page<Bbs> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT COUNT(b) FROM Bbs b WHERE (b.bbsTitle LIKE %:keyword% OR b.bbsContent LIKE %:keyword%) AND b.bbsAvailable = true")
	long countByKeyword(@Param("keyword") String keyword);

	@Query("SELECT b FROM Bbs b WHERE b.bbsAvailable = true")
	Page<Bbs> findAllAvailable(Pageable pageable);

	long countByBbsAvailableTrue();
}