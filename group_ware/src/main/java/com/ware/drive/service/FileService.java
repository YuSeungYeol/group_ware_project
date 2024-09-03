package com.ware.spring.drive.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.drive.repository.FolderRepository;

@Service
public class FileService {
	
	private final FolderRepository folderRepository;
	
	@Autowired
	public FileService(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}
	
	public String upload(MultipartFile file, String targetDir) {
		String newFileName = null;
		try {
			// 1. 파일 원래 이름
			String oriFileName = file.getOriginalFilename();
			// 2. 파일 자르기
			String fileExt = oriFileName.substring(oriFileName.lastIndexOf("."));
			if(!isValidExtension(fileExt)) {
				throw new IllegalArgumentException("허용되지 않은 파일 형식입니다.");
			}
			// 3. 파일 명칭 바꾸기
			UUID uuid = UUID.randomUUID();
			// 4. 8자리마다 포함되는 하이픈 제거
			String uniqueName = uuid.toString().replaceAll("-", "");
			// 5. 새로운 파일명
			newFileName = uniqueName+fileExt;
			// 7. 파일 껍데기 생성
			File saveFile = new File(targetDir+ File.separator +newFileName);
			// 8. 경로 존재 여부 확인
			if(!saveFile.exists()) {
				saveFile.mkdirs();
			}
			// 9. 껍데기에 파일 정보 복제
			file.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}
	
	private boolean isValidExtension(String ext) {
		List<String> allowedExtensions = Arrays.asList(        ".jpg", ".jpeg", ".png", ".gif", // 이미지 파일
		        ".pdf", ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", // 문서 파일
		        ".mp4", ".avi", ".mov", ".wmv", // 영상 파일
		        ".zip", ".rar", ".7z", // 압축 파일
		        ".mp3", ".wav"); // 오디오 파일);
		return allowedExtensions.contains(ext.toLowerCase());
	}
}
