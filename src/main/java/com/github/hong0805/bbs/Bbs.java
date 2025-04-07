package com.github.hong0805.bbs;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Bbs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bbsID;

	@Column(nullable = false)
	private String bbsTitle;

	@Column(nullable = false)
	private String userID;

	@Column(nullable = false)
	private LocalDateTime bbsDate = LocalDateTime.now();

	@Column(columnDefinition = "TEXT", nullable = false)
	private String bbsContent;

	@Column(nullable = false)
	private boolean bbsAvailable = true;

	@Column(nullable = false)
	private int cost = 0;

	private String bbsImage;

	public Bbs(int bbsID) {
		this.bbsID = bbsID;
	}
}