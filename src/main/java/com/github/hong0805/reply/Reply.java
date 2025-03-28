package com.github.hong0805.reply;

import lombok.Data;

import javax.persistence.*;

import com.github.hong0805.bbs.Bbs;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "REPLY")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int replyID;

	@ManyToOne
	@JoinColumn(name = "bbsID")
	private Bbs bbs;

	private String replyContent;
	private String userID;
	private int replyAvailable;
	private Timestamp replyDate;
}
