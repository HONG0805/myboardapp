package com.github.hong0805.bbs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BbsService {

	@Autowired
	private BbsRepository bbsRepository;

	// 전체 게시글 조회 (페이징 포함)
	public List<Bbs> getAllBbs(int limit, int offset) {
		Pageable pageable = PageRequest.of(offset / limit, limit);
		Page<Bbs> page = bbsRepository.findAll(pageable);
		return page.getContent();
	}

	// ID로 게시글 1개 조회
	public Bbs getBbsById(int bbsID) {
		return bbsRepository.findById(bbsID).orElse(null);
	}

	// 게시글 저장 (등록/수정)
	public void saveBbs(Bbs bbs) {
		bbsRepository.save(bbs);
	}

	// 게시글 삭제
	public void deleteBbs(int bbsID) {
		bbsRepository.deleteById(bbsID);
	}

	// 검색어 기반 게시글 조회 (페이징 포함)
	public List<Bbs> searchBbs(String keyword, int limit, int offset) {
		Pageable pageable = PageRequest.of(offset / limit, limit);
		Page<Bbs> page = bbsRepository.searchByKeyword(keyword, pageable);
		return page.getContent();
	}

	// 총 게시글 수 반환 (검색어 포함 가능)
	public int getTotalCount(String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return (int) bbsRepository.count();
		} else {
			return bbsRepository.countByKeyword(keyword);
		}
	}

	// 페이지 단위 게시글 조회 (비로그인 메인페이지용)
	public List<Bbs> getBbsList(int pageNumber, String searchWord) {
		int pageSize = 10;
		int offset = (pageNumber - 1) * pageSize;

		if (searchWord == null || searchWord.isEmpty()) {
			return getAllBbs(pageSize, offset);
		} else {
			return searchBbs(searchWord, pageSize, offset);
		}
	}
}
