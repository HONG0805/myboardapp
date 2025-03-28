package com.github.hong0805.jjim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hong0805.bbs.Bbs;
import com.github.hong0805.bbs.BbsRepository;

import java.util.List;

@Service
public class JjimService {

	@Autowired
	private JjimRepository jjimRepository;

	@Autowired
	private BbsRepository bbsRepository; // 게시글 조회를 위해 BbsRepository를 사용

	// 찜 리스트 조회
	public List<Bbs> getList(String userID, int pageNumber) {
		List<Bbs> bbsList = bbsRepository.findJjimListByUserID(userID, (pageNumber - 1) * 10, 10);
		return bbsList;
	}

	// 찜 추가
	public String addJjim(String userID, int bbsID) {
		if (jjimRepository.existsByUserIDAndBbsID(userID, bbsID)) {
			return "이미 찜한 게시물입니다.";
		}
		Jjim jjim = new Jjim();
		jjim.setUserID(userID);
		jjim.setBbsID(bbsID);
		jjimRepository.save(jjim);
		return "찜 추가 성공!";
	}

	// 찜 삭제
	public String deleteJjim(String userID, int bbsID) {
		if (!jjimRepository.existsByUserIDAndBbsID(userID, bbsID)) {
			return "찜한 게시물이 아닙니다.";
		}
		jjimRepository.deleteByUserIDAndBbsID(userID, bbsID);
		return "찜 삭제 성공!";
	}

	// 사용자별 찜 게시물 수 조회
	public int getTotalCount(String userID) {
		return jjimRepository.findByUserID(userID).size();
	}

	// 다음 페이지 여부 확인
	public boolean hasNextPage(String userID, int pageNumber) {
		int totalCount = getTotalCount(userID);
		int pageSize = 10;
		return totalCount > pageNumber * pageSize;
	}
}
