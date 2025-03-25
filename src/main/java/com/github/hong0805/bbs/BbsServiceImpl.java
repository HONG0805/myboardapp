package com.github.hong0805.bbs;

import com.github.hong0805.bbs.dto.request.*;
import com.github.hong0805.bbs.dto.response.*;
import com.github.hong0805.bbs.BbsPaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BbsServiceImpl implements BbsService {

	private final BbsRepository bbsRepository;

	@Override
	public BbsDetailResponse createBbs(BbsCreateRequest request) {
		Bbs bbs = Bbs.builder().bbsTitle(request.getTitle()).userID(request.getUserID())
				.bbsContent(request.getContent()).cost(request.getCost()).bbsAvailable(1).build();

		Bbs savedBbs = bbsRepository.save(bbs);
		return BbsDetailResponse.from(savedBbs);
	}

	@Override
	@Transactional(readOnly = true)
	public BbsDetailResponse getBbs(Long bbsID) {
		Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new BbsNotFoundException(bbsID));

		if (bbs.getBbsAvailable() == 0) {
			throw new BbsNotFoundException(bbsID);
		}

		return BbsDetailResponse.from(bbs);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BbsListResponse> getBbsList(int page) {
		Pageable pageable = BbsPaginationUtil.createPageable(page);
		Page<Bbs> bbsPage = bbsRepository.findByBbsAvailable(1, pageable);

		return new PageImpl<>(bbsPage.getContent().stream().map(BbsListResponse::from).collect(Collectors.toList()),
				pageable, bbsPage.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BbsListResponse> searchBbs(BbsSearchRequest request) {
		Pageable pageable = BbsPaginationUtil.createPageable(request.getPage());
		Page<Bbs> bbsPage = bbsRepository.searchBbs(request.getKeyword(), pageable);

		return new PageImpl<>(bbsPage.getContent().stream().map(BbsListResponse::from).collect(Collectors.toList()),
				pageable, bbsPage.getTotalElements());
	}

	@Override
	public BbsDetailResponse updateBbs(Long bbsID, BbsUpdateRequest request) {
		Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new BbsNotFoundException(bbsID));

		if (bbs.getBbsAvailable() == 0) {
			throw new BbsNotFoundException(bbsID);
		}

		bbs.setBbsTitle(request.getTitle());
		bbs.setBbsContent(request.getContent());
		bbs.setCost(request.getCost());

		Bbs updatedBbs = bbsRepository.save(bbs);
		return BbsDetailResponse.from(updatedBbs);
	}

	@Override
	public void deleteBbs(Long bbsID) {
		Bbs bbs = bbsRepository.findById(bbsID).orElseThrow(() -> new BbsNotFoundException(bbsID));

		bbs.setBbsAvailable(0);
		bbsRepository.save(bbs);
	}
}