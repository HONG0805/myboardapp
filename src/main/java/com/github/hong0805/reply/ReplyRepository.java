package com.github.hong0805.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	List<Reply> findByBbs_BbsIDAndReplyAvailable(int bbsID, int replyAvailable);

	List<Reply> findByBbs_BbsIDAndReplyAvailableOrderByReplyIDDesc(int bbsID, int replyAvailable);
}
