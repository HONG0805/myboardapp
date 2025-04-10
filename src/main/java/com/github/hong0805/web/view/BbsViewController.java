package com.github.hong0805.web.view;

import com.github.hong0805.domain.Bbs;
import com.github.hong0805.service.BbsService;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bbs")
public class BbsViewController {

	private final BbsService bbsService;

	// 메인 페이지
	@GetMapping("/main")
	public String mainPage(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "searchWord", required = false) String searchWord, Model model,
			Authentication authentication) {

		// 로그인한 사용자 ID 전달 (MainPage.html에서 userID 활용)
		if (authentication != null && authentication.getPrincipal() instanceof String) {
			model.addAttribute("userID", authentication.getPrincipal());
		}

		// 게시글 목록 조회
		BbsSearch search = new BbsSearch(searchWord);
		Pageable pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("bbsDate").descending());
		Page<BbsResponse> postPage = bbsService.getPostList(search, pageable);

		model.addAttribute("bbsList", postPage.getContent());
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", postPage.getTotalPages());
		model.addAttribute("searchWord", searchWord);

		return "MainPage";
	}

	// 게시글 작성 페이지
	@GetMapping("/write")
	public String writeForm() {
		return "Board";
	}

	// 게시글 수정 페이지
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		BbsResponse post = bbsService.getPostById(id);
		model.addAttribute("post", post);
		return "update";
	}

	// 게시글 상세 보기
	@GetMapping("/view/{id}")
	public String viewPost(@PathVariable Long id, Model model) {
		BbsResponse post = bbsService.getPostById(id);
		model.addAttribute("post", post);
		return "view";
	}

	// 채팅 페이지
	@GetMapping("/chat/{id}")
	public String chatPage(@PathVariable Long id, Model model) {
		BbsResponse post = bbsService.getPostById(id);
		model.addAttribute("post", post);
		return "Chat";
	}

	// 마이페이지
	@GetMapping("/mypage")
	public String myPage() {
		return "MyPage";
	}

	// 검색 결과 페이지
	@GetMapping("/search")
	public String searchResult(@ModelAttribute BbsSearch search, @PageableDefault(size = 10) Pageable pageable,
			Model model) {
		model.addAttribute("bbsList", bbsService.getPostList(search, pageable));
		return "searchedBbs";
	}

	// 찜 목록 페이지
	@GetMapping("/jjim")
	public String jjimList() {
		return "jjimBbs";
	}
}
