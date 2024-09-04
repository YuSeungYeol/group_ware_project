package com.ware.spring.notice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.board.domain.BoardDto;
import com.ware.spring.notice.domain.NoticeDto;
import com.ware.spring.notice.service.NoticeFileService;
import com.ware.spring.notice.service.NoticeService;

@Controller
public class NoticeApiController {

	private final NoticeService noticeService;
	private final NoticeFileService fileService; 
	
	@Autowired
	public NoticeApiController(NoticeService noticeService, NoticeFileService fileService) {
		this.noticeService = noticeService;
		this.fileService = fileService;
	}
	
	@ResponseBody
	@PostMapping("/notice")
	public Map<String,String> createNotice(NoticeDto dto, 
			@RequestParam("file") MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 등록중 오류가 발생했습니다.");
		
		String savedFileName = fileService.upload(file);
		if(savedFileName != null) {
			dto.setNotice_ori_thumbnail(file.getOriginalFilename());
			dto.setNotice_new_thumbnail(savedFileName);
			if(noticeService.createNotice(dto) != null) {
				resultMap.put("res_code", "200");
				resultMap.put("res_msg", "게시글이 성공적으로 등록되었습니다.");
			} 
		} else {
			resultMap.put("res_msg", "파일 업로드가 실패하였습니다");
		}
		
		return resultMap;
	}
	
	@GetMapping("/download/{notice_no}")
	public ResponseEntity<Object> noticeImgDownload(
			@PathVariable("notice_no")Long notice_no){
		return fileService.download(notice_no);
	}
	
	@ResponseBody
	@PostMapping("/notice/{notice_no}")
	public Map<String,String> updateNotice(NoticeDto dto,
			@RequestParam(name="file", required=false)MultipartFile file){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "게시글 수정중 오류가 발생했습니다.");
		
		if(file != null && "".equals(file.getOriginalFilename()) == false) {
			String savedFileName = fileService.upload(file);
			if(savedFileName != null) {
				dto.setNotice_ori_thumbnail(file.getOriginalFilename());
				dto.setNotice_new_thumbnail(savedFileName);
				
				if(fileService.delete(dto.getNotice_no()) > 0) {
					resultMap.put("res_msg", "기존 파일이 정상적으로 삭제 되었습니다.");
				}
 
			} else {
				resultMap.put("res_msg", "파일 업로드가 실패하였습니다.");
			}
		}
		
		if(noticeService.updateNotice(dto) != null) {
			resultMap.put("res_code","200");
			resultMap.put("res_msg", "게시글이 성공적으로 수정되었습니다.");
		}
		
		
		return resultMap;
	}
	
	@ResponseBody
	@DeleteMapping("/notice/{notice_no}")
	public Map<String,String> deleteNotice(@PathVariable("notice_no")Long notice_no){
		Map<String,String> map = new HashMap<String,String>();
		map.put("res_cod","404");
		map.put("res_msg", "게시글 삭제 중 오류가 발생했습니다.");
		
		if(fileService.delete(notice_no) > 0) {
			map.put("res_msg", "기존 파일이 정상적으로 삭제 되었습니다.");
			if(noticeService.deleteNotice(notice_no) >0) {
				map.put("res_code", "200");
				map.put("res_msg", "정상적으로 게시글이 삭제되었습니다.");
			}
		}
		return map;
	}
}
