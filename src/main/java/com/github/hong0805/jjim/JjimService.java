package com.github.hong0805.jjim;

import com.github.hong0805.bbs.Bbs;
import com.github.hong0805.bbs.BbsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class JjimService {

	private static final int PAGE_SIZE = 10;

	private final JjimRepository jjimRepository;
	private final BbsRepository bbsRepository;

	@Autowired
	public JjimService(JjimRepository jjimRepository, BbsRepository bbsRepository) {
		this.jjimRepository = jjimRepository;
		this.bbsRepository = bbsRepository;
	}

	public Page<Bbs> getJjimList(String userID, int pageNumber) {
		PageRequest pageRequest = PageRequest.of(pageNumber - 1, PAGE_SIZE);

		Page<Jjim> jjimPage = jjimRepository.findByUserID(userID, pageRequest);

		List<Bbs> bbsList = jjimPage.getContent().stream().map(jjim -> bbsRepository.findById(jjim.getBbsID()))
				.filter(Optional::isPresent).map(Optional::get).filter(bbs -> bbs.isBbsAvailable()) 
				.collect(Collectors.toList());

		return new PageImpl<>(bbsList, pageRequest, jjimPage.getTotalElements());
	}

	@Transactional
	public void addJjim(String userID, int bbsID) {
		if (jjimRepository.existsByUserIDAndBbsID(userID, bbsID)) {
			throw new IllegalStateException("이미 찜한 게시물입니다.");
		}

		Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

		if (!bbs.isBbsAvailable()) {
			throw new IllegalArgumentException("삭제된 게시물입니다.");
		}

		Jjim jjim = new Jjim(userID, bbsID);
		jjimRepository.save(jjim);
	}

	@Transactional
	public void deleteJjim(String userID, int bbsID) {
		if (!jjimRepository.existsByUserIDAndBbsID(userID, bbsID)) {
			throw new IllegalArgumentException("찜한 기록이 없습니다.");
		}
		jjimRepository.deleteByUserIDAndBbsID(userID, bbsID);
	}

	public long getTotalCount(String userID) {
		return jjimRepository.countByUserID(userID);
	}

	public boolean hasNextPage(String userID, int pageNumber) {
		long totalCount = getTotalCount(userID);
		return totalCount > (long) pageNumber * PAGE_SIZE;
	}
}