package com.github.hong0805.bbs;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bbs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bbsID;

	private String bbsTitle;
	private String userID;

	@CreatedDate
	private LocalDateTime bbsDate;

	@Column(columnDefinition = "TEXT")
	private String bbsContent;

	private int bbsAvailable;
	private int cost;
}