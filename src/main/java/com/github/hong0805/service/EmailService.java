package com.github.hong0805.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender mailSender;

	private final Map<String, String> verificationCodes = new HashMap<>();

	public void saveVerificationCode(String email, String code) {
		verificationCodes.put(email, code);
	}

	public void sendCodeToEmail(String email, String code) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(email);
			helper.setSubject("강남대학교 중고장터 - 이메일 인증 코드");
			helper.setText("<h2>인증코드: <strong>" + code + "</strong></h2><p>해당 코드를 사이트에 입력해주세요.</p>", true);

			mailSender.send(message);
			System.out.println("✅ 이메일 전송 성공: " + email);
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송 실패", e);
		}
	}

	public boolean verifyCode(String email, String code) {
		String savedCode = verificationCodes.get(email);
		return savedCode != null && savedCode.equals(code);
	}
}
