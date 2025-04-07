package com.github.hong0805.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BbsService {

	@Autowired
	private BbsRepository bbsRepository;

	// 게시글 목록 조회 (페이징)
	public Page<Bbs> getBbsList(int pageNumber, int pageSize, String searchWord) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

		if (searchWord != null && !searchWord.trim().isEmpty()) {
			return bbsRepository.searchByKeyword(searchWord, pageable);
		} else {
			return bbsRepository.findAllAvailable(pageable);
		}
	}

	// 게시글 상세 조회
	public Bbs getBbsById(int bbsID) {
		return bbsRepository.findById(bbsID).filter(bbs -> bbs.isBbsAvailable()).orElse(null);
	}

	// 게시글 저장 (생성/수정)
	public Bbs saveBbs(Bbs bbs) {
		return bbsRepository.save(bbs);
	}

	// 게시글 삭제 (논리적 삭제)
	public void deleteBbs(int bbsID) {
		bbsRepository.findById(bbsID).ifPresent(bbs -> {
			bbs.setBbsAvailable(false);
			bbsRepository.save(bbs);
		});
	}

	// 총 게시글 수 조회
	public long getTotalCount(String keyword) {
		if (keyword != null && !keyword.trim().isEmpty()) {
			return bbsRepository.countByKeyword(keyword);
		} else {
			return bbsRepository.countByBbsAvailableTrue();
		}
	}
}