package com.ware.spring.notice.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NoticeDto {

	private Long notice_no;
	private String notice_title;
	private String notice_content;
	private Long notice_writer_no;
	private String notice_writer_name;
	private LocalDateTime notice_reg_date;
	private LocalDateTime notice_mod_date;
	private String notice_ori_thumbnail;
	private String notice_new_thumbnail;
	
	private int search_type = 1;
	private String search_text;
	
	
	public Notice toEntity() {

		return Notice.builder()
				.noticeNo(notice_no)
				.noticeTitle(notice_title)
				.noticeContent(notice_content)
				.noticeOriThumbnail(notice_ori_thumbnail)
				.noticeNewThumbnail(notice_new_thumbnail)
				.noticeRegDate(notice_reg_date)
				.build();
	}

	public NoticeDto toDto(Notice notice) {
		return NoticeDto.builder()
				.notice_no(notice.getNoticeNo())
				.notice_title(notice.getNoticeTitle())
				.notice_content(notice.getNoticeContent())
				.notice_ori_thumbnail(notice.getNoticeOriThumbnail())
				.notice_new_thumbnail(notice.getNoticeNewThumbnail())
				.notice_reg_date(notice.getNoticeRegDate())
				.build();
	}
}
