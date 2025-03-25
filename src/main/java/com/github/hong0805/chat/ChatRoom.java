package com.github.hong0805.chat;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_rooms")
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id; 

	@Column(name = "bbs_id", nullable = false)
	private Long bbsId;

	@Column(name = "user1_id", nullable = false)
	private String user1Id;

	@Column(name = "user2_id", nullable = false)
	private String user2Id;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
}