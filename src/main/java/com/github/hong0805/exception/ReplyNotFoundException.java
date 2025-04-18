package com.github.hong0805.exception;

public class ReplyNotFoundException extends RuntimeException {
	public ReplyNotFoundException(Long replyId) {
		super("ID가 " + replyId + "인 댓글을 찾을 수 없습니다");
	}
}
