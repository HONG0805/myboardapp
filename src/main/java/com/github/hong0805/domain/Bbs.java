package com.github.hong0805.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bbs")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Bbs {

	@OneToMany(mappedBy = "bbs", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();

	@OneToMany(mappedBy = "bbs", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Jjim> jjims = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bbsID")
	private Long bbsID;

	@Column(name = "bbsTitle", nullable = false)
	private String bbsTitle;

	@Column(name = "userID", nullable = false)
	private String userID;

	@Column(name = "bbsDate", nullable = false)
	private LocalDateTime bbsDate;

	@Column(name = "bbsContent", columnDefinition = "TEXT", nullable = false)
	private String bbsContent;

	@Column(name = "bbsAvailable", nullable = false)
	private boolean bbsAvailable;

	@Column(name = "cost", nullable = false)
	private int cost;

	@Column(name = "bbsImage")
	private String bbsImage;
}
