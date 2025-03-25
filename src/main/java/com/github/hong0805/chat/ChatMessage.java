package com.github.hong0805.chat;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat_messages")
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private ChatRoom room;

	@Column(name = "sender_id", nullable = false)
	private String senderId;

	@Column(name = "content", nullable = false, columnDefinition = "TEXT")
	private String content;

	@CreatedDate
	@Column(name = "sent_at", updatable = false)
	private LocalDateTime sentAt;

	public Long getId() {
		return this.id;
	}
}