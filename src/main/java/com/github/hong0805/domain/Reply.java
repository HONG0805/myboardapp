package com.github.hong0805.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bbsID", nullable = false)
	private Bbs bbs;

	@NotBlank
	@Column(nullable = false, length = 1000)
	private String replyContent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userID", nullable = false)
	private User user;

	@Column(nullable = false)
	private boolean replyAvailable = true;

	@Column(nullable = false)
	private LocalDateTime replyDate = LocalDateTime.now();

	@PrePersist
	public void prePersist() {
		this.replyDate = LocalDateTime.now();
	}
}
