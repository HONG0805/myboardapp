package com.github.hong0805.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageID")
	private Long messageID;

	@ManyToOne
	@JoinColumn(name = "roomID", nullable = false)
	private ChatRoom chatRoom;

	@Column(name = "userID", nullable = false)
	private String userID;

	@Column(name = "message", nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@Column(name = "is_read", nullable = false)
	private boolean isRead = false;
}