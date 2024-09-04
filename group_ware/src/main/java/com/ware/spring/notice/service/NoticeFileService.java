package com.ware.spring.notice.service;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.board.domain.Board;
import com.ware.spring.notice.domain.Notice;
import com.ware.spring.notice.repository.NoticeRepository;

@Service
public class NoticeFileService {

	private String fileDir = "C:\\notice\\upload\\";
	
	private final NoticeRepository noticeRepository;
	
	@Autowired
	public NoticeFileService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}
	
	public String upload(MultipartFile file) {
		
		String newFileName = null;
		
		try {
		
			String oriFileName = file.getOriginalFilename();
			String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."), oriFileName.length());
			
			UUID uuid = UUID.randomUUID();
			
			String uniqueName = uuid.toString().replaceAll("-", "");
			newFileName = uniqueName+fileExt;
			File saveFile = new File(fileDir+newFileName);

			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}
	
	public ResponseEntity<Object> download(Long notice_no){
		try {
			Notice n = noticeRepository.findByNoticeNo(notice_no);
			
			String newFileName = n.getNoticeNewThumbnail();
			String oriFileName = URLEncoder.encode(n.getNoticeOriThumbnail(),"UTF-8");
			String downDir = fileDir+newFileName;
			
			Path filePath = Paths.get(downDir); // 경로를 찾아주는 객체
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
		
			File file = new File(downDir);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(oriFileName).build());
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null,HttpStatus.CONFLICT);
		}
	}

	public int delete(Long notice_no){
		int result = -1;
		
		try {
			Notice n = noticeRepository.findByNoticeNo(notice_no);
			
			String newFileName = n.getNoticeNewThumbnail(); //UUID
			String oriFileName = n.getNoticeOriThumbnail(); // 사용자가 아는 파일명
			
			String resultDir = fileDir + URLDecoder.decode(newFileName,"UTF-8");
			
			if(resultDir != null && resultDir.isEmpty() == false) {
				File file = new File(resultDir);
				
				if(file.exists()) {
					file.delete();
					result = 1;
				} 	
			}
		} catch(Exception e) {
			e.printStackTrace();	
		}
		return result;
	} 

}
