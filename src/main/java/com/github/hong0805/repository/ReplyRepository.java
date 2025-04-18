package com.github.hong0805.repository;

import com.github.hong0805.domain.Reply;
import com.github.hong0805.domain.User;
import com.github.hong0805.domain.Bbs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
	List<Reply> findByBbsAndReplyAvailableTrue(Bbs bbs);

	List<Reply> findByUser(User user);

	Optional<Reply> findByReplyIDAndUser(Long replyID, User user);

	Page<Reply> findByBbs(Bbs bbs, Pageable pageable);
}
