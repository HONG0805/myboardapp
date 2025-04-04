package com.github.hong0805.bbs;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
public class Bbs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bbsID;
	private String bbsTitle;
	private String userID;
	private LocalDateTime bbsDate;
	private String bbsContent;
	private boolean bbsAvailable;
	private int cost;
	private String bbsImage;

	// 기본 생성자
	public Bbs() {
	}

	// bbsID로만 생성할 수 있는 생성자
	public Bbs(int bbsID) {
		this.bbsID = bbsID;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getBbsTitle() {
		return bbsTitle;
	}

	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public LocalDateTime getBbsDate() {
		return bbsDate;
	}

	public void setBbsDate(LocalDateTime bbsDate) {
		this.bbsDate = bbsDate;
	}

	public String getBbsContent() {
		return bbsContent;
	}

	public void setBbsContent(String bbsContent) {
		this.bbsContent = bbsContent;
	}

	public boolean getBbsAvailable() {
		return bbsAvailable;
	}

	public void setBbsAvailable(boolean bbsAvailable) {
		this.bbsAvailable = bbsAvailable;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getBbsImage() {
		return bbsImage;
	}

	public void setBbsImage(String bbsImage) {
		this.bbsImage = bbsImage;
	}
}
