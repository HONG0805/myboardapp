package com.github.hong0805.chat;

import javax.persistence.*;

@Entity
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roomID;

	private int bbsID;

	private String user1ID;

	private String user2ID;

	private String partnerID;

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public int getBbsID() {
		return bbsID;
	}

	public void setBbsID(int bbsID) {
		this.bbsID = bbsID;
	}

	public String getUser1ID() {
		return user1ID;
	}

	public void setUser1ID(String user1ID) {
		this.user1ID = user1ID;
	}

	public String getUser2ID() {
		return user2ID;
	}

	public void setUser2ID(String user2ID) {
		this.user2ID = user2ID;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
}
