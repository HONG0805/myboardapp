package com.github.hong0805.jjim;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jjim")
@IdClass(Jjim.JjimId.class)
public class Jjim {

	@Id
	@Column(nullable = false)
	private String userID;

	@Id
	@Column(nullable = false)
	private int bbsID;

	// 복합 키를 위한 내부 클래스
	public static class JjimId implements Serializable {
		private String userID;
		private int bbsID;
	}

	public Jjim() {
	}

	public Jjim(String userID, int bbsID) {
		this.userID = userID;
		this.bbsID = bbsID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}
}