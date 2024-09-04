package com.ware.spring.notice.service;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ware.spring.board.domain.Board;
import com.ware.spring.board.domain.BoardDto;
import com.ware.spring.member.domain.Member;
import com.ware.spring.notice.domain.Notice;
import com.ware.spring.notice.domain.NoticeDto;
import com.ware.spring.notice.repository.NoticeRepository;
import com.ware.spring.member.repository.MemberRepository;

@Service
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final MemberRepository memberRepository;
	
	@Autowired
	public NoticeService(NoticeRepository noticeRepository, MemberRepository memberRepository) {
		this.noticeRepository = noticeRepository;
		this.memberRepository = memberRepository;
	}
	
	public Page<NoticeDto> selectNoticeList(NoticeDto searchDto,Pageable pageable){
		Page<Notice> noticeList = null;

		String searchText = searchDto.getSearch_text();
		if(searchText != null && "".equals(searchText) == false) {
			int searchType = searchDto.getSearch_type();
			switch(searchType) {
			case 1 : noticeList = noticeRepository.findBynoticeTitleContaining(searchText,pageable); break;
			case 2 : noticeList = noticeRepository.findBynoticeContentContaining(searchText,pageable); break;
			case 3 : noticeList = noticeRepository.findByNoticeTitleOrnoticeContentContaining(searchText,pageable); break; 
			}
		} else {
			noticeList = noticeRepository.findAll(pageable);
		}
		
		List<NoticeDto> noticeDtoList = new ArrayList<NoticeDto>();
		for(Notice n : noticeList) {
			NoticeDto dto = new NoticeDto().toDto(n);
			noticeDtoList.add(dto);
		}
		return new PageImpl<>(noticeDtoList,pageable,noticeList.getTotalElements());
	}
	
	public Notice createNotice(NoticeDto dto) {
		  Long WriterId = dto.getNotice_writer_no();
		  Member member = memberRepository.findByNotices_NoticeNo(WriterId);
			
			Notice notice = Notice.builder()
					.noticeTitle(dto.getNotice_title())
					.noticeContent(dto.getNotice_content())
					.noticeOriThumbnail(dto.getNotice_ori_thumbnail())
					.noticeNewThumbnail(dto.getNotice_new_thumbnail())
					.member(member)
					.build();
					
			return noticeRepository.save(notice);
		}
	
	 public NoticeDto selectNoticeOne(Long notice_no) {
			Notice notice = noticeRepository.findByNoticeNo(notice_no);
			
			NoticeDto dto = NoticeDto.builder()
					
					.notice_no(notice.getNoticeNo())
					.notice_title(notice.getNoticeTitle())
					.notice_content(notice.getNoticeContent())
					.notice_ori_thumbnail(notice.getNoticeOriThumbnail())
					.notice_new_thumbnail(notice.getNoticeNewThumbnail())
					.notice_reg_date(notice.getNoticeRegDate())
					.notice_mod_date(notice.getNoticeModDate())
					.notice_writer_no(notice.getMember().getMemNo()) 
					.notice_writer_name(notice.getMember().getMemName())
					.build();
			
			return dto;
		}
	 
	 public Notice updateNotice(NoticeDto dto) {
			NoticeDto temp = selectNoticeOne(dto.getNotice_no());
			temp.setNotice_title(dto.getNotice_title());
			temp.setNotice_content(dto.getNotice_content());
			if(dto.getNotice_ori_thumbnail() != null
					&& "".equals(dto.getNotice_ori_thumbnail()) == false) {
				temp.setNotice_ori_thumbnail(dto.getNotice_ori_thumbnail());
				temp.setNotice_new_thumbnail(dto.getNotice_new_thumbnail());
			}	
			Notice notice = temp.toEntity();
			Notice result = noticeRepository.save(notice);
			return result;
		}
	 
	 public int deleteNotice(Long notice_no) {
			int result = 0;
			try {
				noticeRepository.deleteById(notice_no);
				result = 1;
			} catch(Exception e) {
				e.printStackTrace();
			}
			return result;
		}
}
