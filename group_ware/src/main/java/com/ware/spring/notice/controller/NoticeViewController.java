package com.ware.spring.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.ware.spring.board.domain.BoardDto;
import com.ware.spring.notice.domain.NoticeDto;
import com.ware.spring.notice.service.NoticeService;

@Controller
public class NoticeViewController {

	private final NoticeService noticeService;
	
	@Autowired
	public NoticeViewController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	@GetMapping("/notice")
	public String selectNoticeList(Model model,
			@PageableDefault(page=0, size=10, sort="noticeRegDate", direction=Sort.Direction.DESC)Pageable pageable,
			@ModelAttribute("searchDto")NoticeDto searchDto) {
		Page<NoticeDto> resultList = noticeService.selectNoticeList(searchDto,pageable);
		
		model.addAttribute("resultList",resultList);
		model.addAttribute("searchDto",searchDto);
		return "notice/list";
	}

	@GetMapping("/notice/create")
	public String createNoticePage() {
		return "notice/create";
	}
	
	@GetMapping("/notice/{notice_no}")
	public String selectBoardOne(Model model,
			  @PathVariable("notice_no") Long notice_no) {
		  NoticeDto dto = noticeService.selectNoticeOne(notice_no);
		  model.addAttribute("dto",dto);
		  return "notice/detail";
	  }
	@GetMapping("/notice/update/{notice_no}")
	public String updateNoticePage(@PathVariable("notice_no")Long notice_no,
			Model model) {
		NoticeDto dto = noticeService.selectNoticeOne(notice_no);
		model.addAttribute("dto",dto);
		return "notice/update";
	}
}
