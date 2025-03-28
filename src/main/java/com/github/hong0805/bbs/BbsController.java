package com.github.hong0805.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bbs")
public class BbsController {

	@Autowired
	private BbsService bbsService;

	// 게시글 리스트 조회
	@GetMapping("/")
	public String getAllBbs(Model model) {
		List<Bbs> bbsList = bbsService.getAllBbs();
		model.addAttribute("bbsList", bbsList);
		return "MainPage"; 
	}

	// 특정 게시글 조회
	@GetMapping("/{bbsID}")
	public String getBbsById(@PathVariable int bbsID, Model model) {
		Bbs bbs = bbsService.getBbsById(bbsID);
		if (bbs != null) {
			model.addAttribute("bbs", bbs);
			return "BoardDetail";
		} else {
			return "redirect:/bbs/"; // 게시글을 찾을 수 없으면 게시글 리스트로 리디렉션
		}
	}

	// 게시글 작성 페이지
	@GetMapping("/create")
	public String createBbsPage() {
		return "CreateBbs"; // CreateBbs.html 템플릿 반환
	}

	// 게시글 작성 처리
	@PostMapping("/create")
	public String createBbs(@RequestParam String bbsTitle, @RequestParam String userID, @RequestParam String bbsContent,
			@RequestParam int cost, @RequestParam(required = false) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		Bbs createdBbs = bbsService.createBbs(bbsTitle, userID, bbsContent, cost, file);
		if (createdBbs != null) {
			redirectAttributes.addFlashAttribute("message", "게시글 작성 성공");
			return "redirect:/bbs/"; // 게시글 작성 후 게시글 리스트로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "게시글 작성 실패");
			return "redirect:/bbs/board"; // 게시글 작성 실패 시 작성 페이지로 리디렉션
		}
	}

	// 게시글 수정 페이지
	@GetMapping("/update/{bbsID}")
	public String updateBbsPage(@PathVariable int bbsID, Model model) {
		Bbs bbs = bbsService.getBbsById(bbsID);
		if (bbs != null) {
			model.addAttribute("bbs", bbs);
			return "UpdateBbs"; // UpdateBbs.html 템플릿 반환
		} else {
			return "redirect:/bbs/"; // 게시글을 찾을 수 없으면 게시글 리스트로 리디렉션
		}
	}

	// 게시글 수정 처리
	@PostMapping("/update/{bbsID}")
	public String updateBbs(@PathVariable int bbsID, @RequestParam String bbsTitle, @RequestParam String bbsContent,
			@RequestParam int cost, @RequestParam(required = false) MultipartFile file,
			RedirectAttributes redirectAttributes) {
		Bbs updatedBbs = bbsService.updateBbs(bbsID, bbsTitle, bbsContent, cost, file);
		if (updatedBbs != null) {
			redirectAttributes.addFlashAttribute("message", "게시글 수정 성공");
			return "redirect:/bbs/"; // 게시글 수정 후 게시글 리스트로 리디렉션
		} else {
			redirectAttributes.addFlashAttribute("message", "게시글 수정 실패");
			return "redirect:/bbs/update/" + bbsID; // 게시글 수정 실패 시 수정 페이지로 리디렉션
		}
	}

	// 게시글 삭제 (논리적 삭제)
	@GetMapping("/delete/{bbsID}")
	public String deleteBbs(@PathVariable int bbsID, RedirectAttributes redirectAttributes) {
		boolean success = bbsService.deleteBbs(bbsID);
		if (success) {
			redirectAttributes.addFlashAttribute("message", "게시글 삭제 성공");
		} else {
			redirectAttributes.addFlashAttribute("message", "게시글 삭제 실패");
		}
		return "redirect:/bbs/"; // 삭제 후 게시글 리스트로 리디렉션
	}
}
