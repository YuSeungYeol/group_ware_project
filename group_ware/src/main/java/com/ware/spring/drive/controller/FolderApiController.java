package com.ware.spring.drive.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ware.spring.drive.domain.FolderDto;
import com.ware.spring.drive.service.FileService;
import com.ware.spring.drive.service.FolderService;

@Controller
public class FolderApiController {

    private final FolderService folderService;
    private final FileService fileService;
    
    @Autowired
    public FolderApiController(FolderService folderService, FileService fileService) {
        this.folderService = folderService;
        this.fileService = fileService;
    }
    
    @ResponseBody
    @PostMapping("/folder/uploadFile")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("folderNo") Long folderNo){
    	Map<String, String> resultMap = new HashMap<String, String>();
    	resultMap.put("res_code", "404");
    	resultMap.put("res_msg", "파일업로드 중 오류가 발생하였습니다.");
    	
    	FolderDto dto = folderService.selecetFolderOne(folderNo);
    	// 폴더 경로
    	String folderPath = dto.getFolder_upload_path();
    	// 파일 경로
    	String newFileName = fileService.upload(file, folderPath);
    	if(newFileName != null) {
    		dto.setFile_ori_name(file.getOriginalFilename());
    		dto.setFile_new_name(newFileName);
    		if(folderService.updateFolder(dto) > 0 ) {
    			resultMap.put("res_code", "200");
    			resultMap.put("res_msg", "파일업로드가 성공하였습니다.");
    		}
    	}else {
    		resultMap.put("res_msg", "파일업로드가 실패하였습니다.");
    	}
    	return resultMap;
    }
    
    @ResponseBody
    @PostMapping("/folder/updateDelYn")
    public Map<String, String> updateDelYn(@RequestBody Map<String, List<Long>> request) {
        Map<String, String> resultMap = new HashMap<>();
        List<Long> folderNos = request.get("folderNos");

        resultMap.put("res_code", "404");
        resultMap.put("res_msg", "폴더 삭제가 실패하였습니다.");

        for (Long folderNo : folderNos) {
            FolderDto folderDto = new FolderDto();
            folderDto.setFolder_no(folderNo);           
            folderDto.setDel_yn("Y");

            int result = folderService.updateFolder(folderDto);
            if (result > 0) {
                resultMap.put("res_code", "200");
                resultMap.put("res_msg", "폴더 삭제가 성공하였습니다.");
            } else {
                resultMap.put("res_code", "404");
                resultMap.put("res_msg", "일부 폴더 삭제가 실패하였습니다.");
                break;  // 하나라도 실패하면 중지하고 오류 메시지를 반환합니다.
            }
        }

        return resultMap;
    }
    
    
    
    @ResponseBody
    @PostMapping("/folder/create")
    public Map<String, String> createFolder(@RequestBody FolderDto dto, @RequestParam("parentFolderNo") Long parentFolderNo) {
        Map<String, String> resultMap = new HashMap<>();
        System.out.println("Received parentFolderNo: " + parentFolderNo); // 이 줄을 추가
        resultMap.put("res_code", "404");
        resultMap.put("res_msg", "폴더 생성이 실패하였습니다.");

        if (parentFolderNo != null && parentFolderNo > 0) {
            FolderDto parentFolder = folderService.selectFolderById(parentFolderNo);
            if (parentFolder != null) {
            	dto.setParentFolder(parentFolder.toEntity());
                System.out.println("Set Parent Folder ID in DTO: " + dto.getParentFolder());
                // 하위 폴더 경로 설정
                String folderPath = parentFolder.getFolder_upload_path() + "/" + dto.getFolder_name();
                dto.setFolder_upload_path(folderPath);
            }
        }else {
        	// 최상위 폴더의 경우
        	String folderPath = "C:/uploads/" + dto.getFolder_name();
        	dto.setFolder_upload_path(folderPath);
        }

        if (folderService.createFolder(dto) > 0) {
            resultMap.put("res_code", "200");
            resultMap.put("res_msg", "폴더 생성이 성공하였습니다.");
        }

        return resultMap;
    }
    
    @ResponseBody
    @GetMapping("/folder/apiList")
    public List<FolderDto> getFolderContents(@RequestParam("parentFolderNo") Long parentFolderNo) {
        return folderService.getSubFoldersAndFiles(parentFolderNo);
    }
    
}


