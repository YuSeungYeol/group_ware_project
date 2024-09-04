package com.ware.spring.board.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ware.spring.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name="board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Board {

	// 아래 내용이 mvc_study에 board를 가져오는 코드
		@Id
		@Column(name="board_no")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long boardNo;
		
		@Column(name="board_title")
		private String boardTitle;
		
		@Column(name="board_content")
		private String boardContent;
		
		@ManyToOne
		@JoinColumn(name="board_writer")
		private Member member;
		
		@Column(name="reg_date")
		@CreationTimestamp
		private LocalDateTime regDate;
		
		@Column(name="mod_date")
		@UpdateTimestamp
		private LocalDateTime modDate;
		
		@Column(name="ori_thumbnail")
		private String oriThumbnail;
		
		@Column(name="new_thumbnail")
		private String newThumbnail;
		

	 
}
