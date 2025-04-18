package com.github.hong0805.service;

import com.github.hong0805.domain.Bbs;
import com.github.hong0805.domain.Jjim;
import com.github.hong0805.domain.User;
import com.github.hong0805.repository.JjimRepository;
import com.github.hong0805.repository.BbsRepository;
import com.github.hong0805.repository.UserRepository;
import com.github.hong0805.web.dto.jjim.JjimResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JjimService {

	private final JjimRepository jjimRepository;
	private final BbsRepository bbsRepository;
	private final UserRepository userRepository;

	// 찜 추가
	public void addJjim(Long bbsId, String userId) {
		Bbs bbs = bbsRepository.findById(bbsId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
		Jjim jjim = new Jjim(bbs, user);
		jjimRepository.save(jjim);
	}

	// 찜 해제
	@Transactional
	public void removeJjim(Long bbsId, String userId) {
		// 찜이 존재하지 않으면 예외 처리
		long count = jjimRepository.countByBbs_BbsIDAndUser_UserId(bbsId, userId);
		if (count == 0) {
			throw new RuntimeException("찜을 찾을 수 없습니다.");
		}

		// 찜을 삭제
		jjimRepository.deleteByBbs_BbsIDAndUser_UserId(bbsId, userId);
	}

	// 사용자 ID로 찜 목록 조회
	public List<JjimResponse> getUserJjims(String userId) {
		List<Jjim> jjimList = jjimRepository.findByUser_UserId(userId);
		return jjimList.stream().map(jjim -> new JjimResponse(jjim)).collect(Collectors.toList());
	}

	// 찜 여부 확인
	public boolean checkIfAlreadyJjimmed(Long bbsId, String userId) {
		return jjimRepository.findByBbs_BbsIDAndUser_UserId(bbsId, userId).isPresent();
	}
}
