package com.github.hong0805.bbs;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BbsRepository extends JpaRepository<Bbs, Integer> {

	@Query("SELECT b FROM Bbs b WHERE b.bbsTitle LIKE %:keyword% OR b.bbsContent LIKE %:keyword%")
	Page<Bbs> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query(value = "SELECT * FROM bbs WHERE bbsTitle LIKE %:keyword% OR bbsContent LIKE %:keyword%", nativeQuery = true)
	List<Bbs> searchBbs(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);
}
