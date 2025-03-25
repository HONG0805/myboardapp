package com.github.hong0805.bbs;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class BbsPaginationUtil {
	public static Pageable createPageable(int page) {
		return PageRequest.of(page - 1, 10); // 1페이지를 0-based로 변환
	}
}