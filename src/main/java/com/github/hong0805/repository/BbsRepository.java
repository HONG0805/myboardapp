package com.github.hong0805.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.hong0805.domain.Bbs;

public interface BbsRepository extends JpaRepository<Bbs, Long> {

	// 게시글 ID로 조회 (기본 제공)
	// Optional<Bbs> findById(Long bbsID);

	// 특정 사용자(userID)가 쓴 게시글 전체 조회
	List<Bbs> findByUserID(String userID);

	// 게시글 제목에 키워드 포함된 것 검색 (부분 검색)
	Page<Bbs> findByBbsTitleContaining(String keyword, Pageable pageable);

	// 내용 검색 메서드
	Page<Bbs> findByBbsContentContaining(String keyword, Pageable pageable);

	// 사용자 ID 검색 메서드 (부분 일치)
	Page<Bbs> findByUserIDContaining(String keyword, Pageable pageable);

	// 사용 가능한 게시글만 조회
	List<Bbs> findByBbsAvailableTrue();

	// 특정 사용자 + 게시 가능 게시글
	List<Bbs> findByUserIDAndBbsAvailableTrue(String userID);

}
