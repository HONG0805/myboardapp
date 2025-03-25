package com.github.hong0805.reply;

import com.github.hong0805.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	Page<Reply> findByBbsIdAndAvailableTrueOrderByIdDesc(Long bbsId, Pageable pageable);

	@Modifying
	@Query("UPDATE Reply r SET r.available = false WHERE r.id = :id")
	int deactivateById(@Param("id") Long id);

	@Modifying
	@Query("UPDATE Reply r SET r.content = :content WHERE r.id = :id")
	int updateContent(@Param("id") Long id, @Param("content") String content);
}