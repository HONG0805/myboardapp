package com.github.hong0805.bbs;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bbs")
public class BbsController {

	@Autowired
	private BbsService bbsService;

	// 게시판 메인 페이지 (페이징 처리)
	@GetMapping("/mainpage")
	public String mainPage(Model model, @RequestParam(defaultValue = "1") int pageNumber,
			@RequestParam(required = false) String searchWord, HttpSession session) {

		// 세션에서 사용자 ID 확인
		String userID = (String) session.getAttribute("userID");
		if (userID == null) {
			return "redirect:/user/login";
		}
		model.addAttribute("userID", userID);

		// 페이징 설정
		int pageSize = 10;
		int offset = (pageNumber - 1) * pageSize;

		// 검색 여부에 따라 데이터 조회
		List<Bbs> bbsList;
		if (searchWord != null && !searchWord.trim().isEmpty()) {
			bbsList = bbsService.searchBbs(searchWord, pageSize, offset);
			model.addAttribute("searchWord", searchWord);
		} else {
			bbsList = bbsService.getAllBbs(pageSize, offset);
		}

		// 총 게시물 수 계산 (추가 구현 필요)
		int totalCount = bbsService.getTotalCount(searchWord);
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);

		// 모델에 데이터 추가
		model.addAttribute("bbsList", bbsList);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", totalPages);

		return "MainPage"; 
	}

	// 게시글 상세 보기
	@GetMapping("/view/{id}")
	public String viewBbs(@PathVariable("id") int bbsID, Model model) {
		Bbs bbs = bbsService.getBbsById(bbsID);
		if (bbs == null) {
			return "redirect:/bbs/mainpage"; 
		}
		model.addAttribute("bbs", bbs);
		return "bbs/view"; 
	}

	// 글쓰기 폼
	@GetMapping("/write")
	public String writeForm(HttpSession session, Model model) {
		String userID = (String) session.getAttribute("userID");
		if (userID == null) {
			return "redirect:/user/login";
		}

		Bbs bbs = new Bbs();
		bbs.setUserID(userID); 
		model.addAttribute("bbs", bbs);
		return "bbs/Board"; 
	}

	// 글쓰기 처리
	@PostMapping("/write")
	public String writeBbs(@ModelAttribute Bbs bbs, HttpSession session, RedirectAttributes redirectAttributes) {

		String userID = (String) session.getAttribute("userID");
		if (userID == null) {
			return "redirect:/user/login";
		}

		bbs.setUserID(userID);
		bbsService.saveBbs(bbs);

		redirectAttributes.addFlashAttribute("message", "글이 등록되었습니다.");
		return "redirect:/bbs/mainpage";
	}

	// 글 수정 폼
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") int bbsID, HttpSession session, Model model) {

		String userID = (String) session.getAttribute("userID");
		Bbs bbs = bbsService.getBbsById(bbsID);

		// 권한 확인: 작성자만 수정 가능
		if (bbs == null || !bbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		model.addAttribute("bbs", bbs);
		return "bbs/update";
	}

	// 글 수정 처리
	@PostMapping("/edit/{id}")
	public String editBbs(@PathVariable("id") int bbsID, @ModelAttribute Bbs updatedBbs, HttpSession session,
			RedirectAttributes redirectAttributes) {

		String userID = (String) session.getAttribute("userID");
		Bbs existingBbs = bbsService.getBbsById(bbsID);

		// 권한 확인
		if (existingBbs == null || !existingBbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		// 기존 데이터 업데이트
		existingBbs.setBbsTitle(updatedBbs.getBbsTitle());
		existingBbs.setBbsContent(updatedBbs.getBbsContent());
		bbsService.saveBbs(existingBbs);

		redirectAttributes.addFlashAttribute("message", "글이 수정되었습니다.");
		return "redirect:/bbs/view/" + bbsID;
	}

	// 글 삭제 처리
	@PostMapping("/delete/{id}")
	public String deleteBbs(@PathVariable("id") int bbsID, HttpSession session, RedirectAttributes redirectAttributes) {

		String userID = (String) session.getAttribute("userID");
		Bbs bbs = bbsService.getBbsById(bbsID);

		// 권한 확인
		if (bbs == null || !bbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		bbsService.deleteBbs(bbsID);
		redirectAttributes.addFlashAttribute("message", "글이 삭제되었습니다.");
		return "redirect:/bbs/mainpage";
	}
}