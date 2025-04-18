package com.github.hong0805.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Jjim {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jjimID;

	@ManyToOne
	@JoinColumn(name = "bbsID")
	private Bbs bbs;

	@ManyToOne
	@JoinColumn(name = "userID")
	private User user; 

	@Column(nullable = false)
	private LocalDateTime jjimDate;

	public Jjim(Bbs bbs, User user) {
		this.bbs = bbs;
		this.user = user;
		this.jjimDate = LocalDateTime.now();
	}
}
