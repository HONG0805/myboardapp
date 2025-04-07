package com.github.hong0805.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/bbs")
public class BbsController {

	private final BbsService bbsService;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@Autowired
	public BbsController(BbsService bbsService) {
		this.bbsService = bbsService;
	}

	@GetMapping({ "", "/" })
	public String rootRedirect(HttpSession session) {
		if (session.getAttribute("userID") == null) {
			return "redirect:/user/login";
		}
		return "redirect:/bbs/mainpage";
	}

	// 게시판 메인 페이지
	@GetMapping("/mainpage")
	public String mainPage(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String searchWord, HttpSession session, Model model) {

		String userID = (String) session.getAttribute("userID");
		if (userID == null) {
			return "redirect:/user/login";
		}

		Page<Bbs> bbsPage = bbsService.getBbsList(page, DEFAULT_PAGE_SIZE, searchWord);

		model.addAttribute("userID", userID);
		model.addAttribute("bbsList", bbsPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", bbsPage.getTotalPages());
		model.addAttribute("searchWord", searchWord);

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
		return "view";
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
		return "Board";
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

		if (bbs == null || !bbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		model.addAttribute("bbs", bbs);
		return "update";
	}

	// 글 수정 처리
	@PostMapping("/edit/{id}")
	public String editBbs(@PathVariable("id") int bbsID, @ModelAttribute Bbs updatedBbs, HttpSession session,
			RedirectAttributes redirectAttributes) {

		String userID = (String) session.getAttribute("userID");
		Bbs existingBbs = bbsService.getBbsById(bbsID);

		if (existingBbs == null || !existingBbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		existingBbs.setBbsTitle(updatedBbs.getBbsTitle());
		existingBbs.setBbsContent(updatedBbs.getBbsContent());
		existingBbs.setBbsImage(updatedBbs.getBbsImage());
		existingBbs.setCost(updatedBbs.getCost());

		bbsService.saveBbs(existingBbs);

		redirectAttributes.addFlashAttribute("message", "글이 수정되었습니다.");
		return "redirect:/bbs/view/" + bbsID;
	}

	// 글 삭제 처리
	@PostMapping("/delete/{id}")
	public String deleteBbs(@PathVariable("id") int bbsID, HttpSession session, RedirectAttributes redirectAttributes) {

		String userID = (String) session.getAttribute("userID");
		Bbs bbs = bbsService.getBbsById(bbsID);

		if (bbs == null || !bbs.getUserID().equals(userID)) {
			return "redirect:/bbs/mainpage";
		}

		bbsService.deleteBbs(bbsID);
		redirectAttributes.addFlashAttribute("message", "글이 삭제되었습니다.");
		return "redirect:/bbs/mainpage";
	}
}