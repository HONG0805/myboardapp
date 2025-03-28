package com.github.hong0805.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public List<Reply> getReplies(int bbsID, int pageNumber) {
		// Pagination logic for 10 replies per page
		int offset = (pageNumber - 1) * 10;
		return replyRepository.findByBbs_BbsIDAndReplyAvailableOrderByReplyIDDesc(bbsID, 1).subList(offset,
				Math.min(offset + 10, replyRepository.findByBbs_BbsIDAndReplyAvailable(bbsID, 1).size()));
	}

	public Reply getReply(int replyID) {
		return replyRepository.findById(replyID).orElse(null);
	}

	public Reply createReply(Reply reply) {
		return replyRepository.save(reply);
	}

	public Reply updateReply(int replyID, String content) {
		Reply reply = replyRepository.findById(replyID).orElse(null);
		if (reply != null) {
			reply.setReplyContent(content);
			return replyRepository.save(reply);
		}
		return null;
	}

	public boolean deleteReply(int replyID) {
		Reply reply = replyRepository.findById(replyID).orElse(null);
		if (reply != null) {
			reply.setReplyAvailable(0);
			replyRepository.save(reply);
			return true;
		}
		return false;
	}
}
