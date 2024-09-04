package com.ware.spring.notice.domain;

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
@Table(name="notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Notice {
	
	@Id
	@Column(name="notice_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeNo;
	
	@Column(name="notice_title")
	private String noticeTitle;
	
	@Column(name="notice_content")
	private String noticeContent;
	
	@ManyToOne
	@JoinColumn(name="notice_writer")
	private Member member;
	
	@Column(name="notice_reg_date")
	@CreationTimestamp
	private LocalDateTime noticeRegDate;
	
	@Column(name="notice_mod_date")
	@UpdateTimestamp
	private LocalDateTime noticeModDate;
	
	@Column(name="notice_ori_thumbnail")
	private String noticeOriThumbnail;
	
	@Column(name="notice_new_thumbnail")
	private String noticeNewThumbnail;
	

}
