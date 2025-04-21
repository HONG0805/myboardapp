package com.github.hong0805.service;

import com.github.hong0805.domain.Bbs;
import com.github.hong0805.repository.BbsRepository;
import com.github.hong0805.web.dto.bbs.BbsRequest;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BbsService {

	private final BbsRepository bbsRepository;

	@Value("${bbs.upload-dir}")
	private String uploadDir;

	// 게시글 등록
	public void createPost(BbsRequest request, MultipartFile file, String userId) throws IOException {
		String bbsImage = null;

		if (file != null && !file.isEmpty()) {
			bbsImage = uploadImage(file);
		}

		Bbs post = Bbs.builder().bbsTitle(request.getBbsTitle()).bbsContent(request.getBbsContent()).userID(userId)
				.bbsDate(LocalDateTime.now()).bbsAvailable(true).cost(request.getCost()).bbsImage(bbsImage).build();

		bbsRepository.save(post);
	}

	// 이미지 업로드 처리
	private String uploadImage(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || !originalFilename.matches(".*\\.(jpg|jpeg|png|gif)$")) {
			throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
		}

		String fileName = UUID.randomUUID() + "_" + originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
		Path path = Paths.get(uploadDir, fileName);

		Files.createDirectories(path.getParent());
		file.transferTo(path.toFile());

		return fileName;
	}

	// 게시글 수정
	public void updatePost(Long id, BbsRequest request, MultipartFile file) throws IOException {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

		if (file != null && !file.isEmpty()) {
			if (post.getBbsImage() != null) {
				Path oldImagePath = Paths.get(uploadDir, post.getBbsImage());
				Files.deleteIfExists(oldImagePath);
			}
			String newImageName = uploadImage(file);
			post.setBbsImage(newImageName);
		} else if (request.getBbsImage() == null) {
			if (post.getBbsImage() != null) {
				Path oldImagePath = Paths.get(uploadDir, post.getBbsImage());
				Files.deleteIfExists(oldImagePath);
			}
			post.setBbsImage(null);
		}

		post.setBbsTitle(request.getBbsTitle());
		post.setBbsContent(request.getBbsContent());
		post.setCost(request.getCost());

		bbsRepository.save(post);
	}

	// 게시글 삭제
	public void deletePost(Long id) throws IOException {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

		if (post.getBbsImage() != null) {
			Path imagePath = Paths.get(uploadDir, post.getBbsImage());
			Files.deleteIfExists(imagePath);
		}

		bbsRepository.delete(post);
	}

	// 게시글 단일 조회
	public BbsResponse getPostById(Long id) {
		Bbs post = bbsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

		// 이미지 파일명만 전달
		String imageFileName = post.getBbsImage();

		return BbsResponse.builder().bbsID(post.getBbsID()).bbsTitle(post.getBbsTitle())
				.bbsContent(post.getBbsContent()).userID(post.getUserID()).bbsDate(post.getBbsDate())
				.bbsAvailable(post.isBbsAvailable()).cost(post.getCost()).bbsImage(imageFileName).build();
	}

	// 게시글 목록 조회 + 검색 + 페이징
	public Page<BbsResponse> getPostList(BbsSearch search, Pageable pageable) {
		Page<Bbs> page;
		if (search.getKeyword() == null || search.getKeyword().trim().isEmpty()) {
			page = bbsRepository.findAll(pageable);
		} else {
			switch (search.getSearchType()) {
			case "content":
				page = bbsRepository.findByBbsContentContaining(search.getKeyword(), pageable);
				break;
			case "user":
				page = bbsRepository.findByUserIDContaining(search.getKeyword(), pageable);
				break;
			default:
				page = bbsRepository.findByBbsTitleContaining(search.getKeyword(), pageable);
			}
		}
		return page.map(this::toResponse);
	}

	// Bbs 엔티티를 BbsResponse로 변환
	private BbsResponse toResponse(Bbs entity) {
		return BbsResponse.builder().bbsID(entity.getBbsID()).bbsTitle(entity.getBbsTitle()).userID(entity.getUserID())
				.userName("사용자 이름 조회 로직").bbsContent(entity.getBbsContent()).bbsDate(entity.getBbsDate())
				.bbsAvailable(entity.isBbsAvailable()).cost(entity.getCost())
				.bbsImage(entity.getBbsImage() != null ? "/api/bbs/images/" + entity.getBbsImage() : null).build();
	}

}
