package com.github.hong0805.service;

import com.github.hong0805.domain.Bbs;
import com.github.hong0805.repository.BbsRepository;
import com.github.hong0805.web.dto.bbs.BbsRequest;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BbsService {

	private final BbsRepository bbsRepository;

	// 게시글 작성
	public void createPost(BbsRequest request) {
		Bbs post = Bbs.builder().bbsTitle(request.getBbsTitle()).bbsContent(request.getBbsContent())
				.userID(request.getUserID()).bbsDate(LocalDateTime.now()).bbsAvailable(true).cost(request.getCost())
				.bbsImage(request.getBbsImage()).build();
		bbsRepository.save(post);
	}

	// 게시글 수정
	public void updatePost(Long id, BbsRequest request) {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		post.setBbsTitle(request.getBbsTitle());
		post.setBbsContent(request.getBbsContent());
		post.setCost(request.getCost());
		post.setBbsImage(request.getBbsImage());
		bbsRepository.save(post);
	}

	// 게시글 삭제
	public void deletePost(Long id) {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		bbsRepository.delete(post);
	}

	// 게시글 단일 조회
	public BbsResponse getPostById(Long id) {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
		return toResponse(post);
	}

	// 게시글 목록 조회 + 검색 + 페이징
	public Page<BbsResponse> getPostList(BbsSearch search, Pageable pageable) {
		Page<Bbs> page;

		if (search.getKeyword() == null || search.getKeyword().trim().isEmpty()) {
			page = bbsRepository.findAll(pageable);
		} else {
			page = bbsRepository.findByBbsTitleContaining(search.getKeyword(), pageable);
		}

		return page.map(this::toResponse);
	}

	private BbsResponse toResponse(Bbs entity) {
		return BbsResponse.builder().bbsID(entity.getBbsID()).bbsTitle(entity.getBbsTitle())
				.bbsContent(entity.getBbsContent()).userID(entity.getUserID()).bbsDate(entity.getBbsDate())
				.bbsAvailable(entity.isBbsAvailable()).cost(entity.getCost()).bbsImage(entity.getBbsImage()).build();
	}
}
