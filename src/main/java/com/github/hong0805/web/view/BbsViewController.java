package com.github.hong0805.web.view;

import com.github.hong0805.service.BbsService;
import com.github.hong0805.service.JjimService;
import com.github.hong0805.service.ReplyService;
import com.github.hong0805.web.dto.bbs.BbsResponse;
import com.github.hong0805.web.dto.bbs.BbsSearch;
import com.github.hong0805.web.dto.reply.ReplyResponse;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
	private final JjimService jjimService;
	private final ReplyService replyService;

	// 메인 페이지
	@GetMapping("/main")
	public String mainPage(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam(value = "searchWord", required = false) String searchWord, Model model,
			Authentication authentication) {

		// 인증 정보 처리
		if (authentication != null && authentication.isAuthenticated()) {
			String userId = authentication.getName();
			model.addAttribute("userID", userId);
			System.out.println("Current userID: " + userId);
		}

		// 기존 게시글 목록 처리 로직
		BbsSearch search = new BbsSearch(searchWord, searchWord);
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
	public String writeForm(Authentication authentication, Model model) {
		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("userID", authentication.getName());
		}
		return "Board";
	}

	// 게시글 수정 페이지
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model, Authentication authentication) {
		BbsResponse post = bbsService.getPostById(id);

		model.addAttribute("post", post);
		model.addAttribute("bbs", post);

		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("userID", authentication.getName());
		}
		return "update";
	}

	// 게시물 상세 보기
	@GetMapping("/view/{id}")
	public String viewPost(@PathVariable Long id, Model model, Authentication authentication) {

		BbsResponse post = bbsService.getPostById(id);

		if (!post.isBbsAvailable()) {
			return "error/404";
		}

		// 이미지 경로를 외부에서 접근 가능한 URL로 변환
		if (post.getBbsImage() != null) {
			post.setBbsImage("/images/" + post.getBbsImage());
		}
		model.addAttribute("post", post);

		boolean isJjim = false;

		// 인증 정보가 있을 경우
		if (authentication != null && authentication.isAuthenticated()) {
			String userId = authentication.getName();
			model.addAttribute("userID", userId);
			System.out.println("Current userID: " + userId);

			// 찜 상태 확인
			isJjim = jjimService.checkIfAlreadyJjimmed(id, userId);
		}

		model.addAttribute("isJjim", isJjim);

		// 댓글 목록 가져오기
		List<ReplyResponse> replyList = replyService.getReplies(id);
		model.addAttribute("replyList", replyList);

		return "view";
	}

	// 채팅 페이지
	@GetMapping("/chat/{id}")
	public String chatPage(@PathVariable Long id, Model model, Authentication authentication) {
		BbsResponse post = bbsService.getPostById(id);
		model.addAttribute("post", post);
		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("userID", authentication.getName());
		}
		return "Chat";
	}

	// 마이페이지
	@GetMapping("/mypage")
	public String myPage(Authentication authentication, Model model) {
		if (authentication != null && authentication.isAuthenticated()) {
			model.addAttribute("userID", authentication.getName());
		}
		return "MyPage";
	}

	// 검색 결과 페이지
	@GetMapping("/search")
	public String searchResult(@ModelAttribute BbsSearch search, @PageableDefault(size = 10) Pageable pageable,
			Model model) {
		model.addAttribute("bbsList", bbsService.getPostList(search, pageable));
		return "searchedBbs";
	}
}
