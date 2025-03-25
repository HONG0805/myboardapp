package com.github.hong0805.bbs;

import com.github.hong0805.bbs.dto.request.*;
import com.github.hong0805.bbs.dto.response.*;
import org.springframework.data.domain.Page;

public interface BbsService {
	BbsDetailResponse createBbs(BbsCreateRequest request);

	BbsDetailResponse getBbs(Long bbsID);

	Page<BbsListResponse> getBbsList(int page);

	Page<BbsListResponse> searchBbs(BbsSearchRequest request);

	BbsDetailResponse updateBbs(Long bbsID, BbsUpdateRequest request);

	void deleteBbs(Long bbsID);
}