package com.github.hong0805.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class BbsService {

	@Autowired
	private BbsRepository bbsRepository;

	private static final String UPLOAD_DIR = "C:/upload/"; // 파일 업로드 경로

	public List<Bbs> getAllBbs() {
		return bbsRepository.findAll();
	}

	public Bbs getBbsById(int bbsID) {
		return bbsRepository.findById(bbsID).orElse(null);
	}

	public Bbs createBbs(String bbsTitle, String userID, String bbsContent, int cost, MultipartFile file) {
		Bbs bbs = new Bbs();
		bbs.setBbsTitle(bbsTitle);
		bbs.setUserID(userID);
		bbs.setBbsDate(String.valueOf(System.currentTimeMillis())); // 현재 시간
		bbs.setBbsContent(bbsContent);
		bbs.setBbsAvailable(1); // 기본값으로 활성화
		bbs.setCost(cost);

		// 파일 업로드 처리
		if (!file.isEmpty()) {
			String filePath = saveFile(file);
			bbs.setBbsImage(filePath);
		}

		return bbsRepository.save(bbs);
	}

	public Bbs updateBbs(int bbsID, String bbsTitle, String bbsContent, int cost, MultipartFile file) {
		Bbs bbs = bbsRepository.findById(bbsID).orElse(null);
		if (bbs != null) {
			bbs.setBbsTitle(bbsTitle);
			bbs.setBbsContent(bbsContent);
			bbs.setCost(cost);

			// 파일 업로드 처리
			if (!file.isEmpty()) {
				String filePath = saveFile(file);
				bbs.setBbsImage(filePath);
			}

			return bbsRepository.save(bbs);
		}
		return null;
	}

	public boolean deleteBbs(int bbsID) {
		Bbs bbs = bbsRepository.findById(bbsID).orElse(null);
		if (bbs != null) {
			bbs.setBbsAvailable(0); // 논리적 삭제
			bbsRepository.save(bbs);
			return true;
		}
		return false;
	}

	// 파일 업로드 메서드
	private String saveFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String filePath = UPLOAD_DIR + fileName;
		File dest = new File(filePath);
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}
}
