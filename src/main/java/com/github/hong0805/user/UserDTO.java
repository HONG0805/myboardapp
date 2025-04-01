package com.github.hong0805.user;

import javax.persistence.Column;
import javax.persistence.Id;

public class UserDTO {
	@Id
	@Column(name = "userID")
	private String userID;

	@Column(name = "userPassword")
	private String userPassword;

	@Column(name = "userEmail")
	private String userEmail;

	@Column(name = "userName")
	private String userName;

	// 기본 생성자
	public UserDTO() {
	}

	// 매개변수 생성자
	public UserDTO(String userID, String userPassword, String userEmail, String userName) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userName = userName;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", userPassword=" + userPassword + ", userEmail=" + userEmail
				+ ", userName=" + userName + "]";
	}
}
