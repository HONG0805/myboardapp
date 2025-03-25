package com.github.hong0805.bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BbsRepository extends JpaRepository<Bbs, Long> {
	@Query("SELECT b FROM Bbs b WHERE b.bbsAvailable = 1 AND "
			+ "(b.bbsTitle LIKE %:keyword% OR b.bbsContent LIKE %:keyword%)")
	Page<Bbs> searchBbs(@Param("keyword") String keyword, Pageable pageable);

	Page<Bbs> findByBbsAvailable(int bbsAvailable, Pageable pageable);
}