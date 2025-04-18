package com.github.hong0805.exception;

public class UnauthorizedReplyAccessException extends RuntimeException {
	public UnauthorizedReplyAccessException(String userId) {
		super("사용자 " + userId + "는 이 댓글을 수정/삭제할 권한이 없습니다");
	}
}
